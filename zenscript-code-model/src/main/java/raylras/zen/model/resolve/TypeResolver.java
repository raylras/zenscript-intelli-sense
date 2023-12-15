package raylras.zen.model.resolve;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import raylras.zen.model.CompilationUnit;
import raylras.zen.model.Compilations;
import raylras.zen.model.Visitor;
import raylras.zen.model.symbol.*;
import raylras.zen.model.type.*;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Executables;
import raylras.zen.util.Operators;
import raylras.zen.util.Symbols;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static raylras.zen.model.parser.ZenScriptParser.*;

public final class TypeResolver {

    private TypeResolver() {}

    public static Optional<Type> getType(ParseTree cst, CompilationUnit unit) {
        return Optional.ofNullable(cst)
                .map(it -> it.accept(new TypeVisitor(unit)));
    }

    private static final class TypeVisitor extends Visitor<Type> {
        final CompilationUnit unit;

        TypeVisitor(CompilationUnit unit) {
            this.unit = unit;
        }

        @Override
        public Type visitImportDeclaration(ImportDeclarationContext ctx) {
            return unit.getSymbol(ctx, ImportSymbol.class)
                    .map(Symbol::getType)
                    .orElse(AnyType.INSTANCE);
        }

        @Override
        public Type visitFunctionDeclaration(FunctionDeclarationContext ctx) {
            List<Type> paramTypes = toTypeList(ctx.formalParameterList());
            Type returnType = visit(ctx.returnType());
            if (returnType == null) {
                returnType = AnyType.INSTANCE;
            }
            return new FunctionType(returnType, paramTypes);
        }

        @Override
        public Type visitExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
            List<Type> paramTypes = toTypeList(ctx.formalParameterList());
            Type returnType = visit(ctx.returnType());
            if (returnType == null) {
                returnType = AnyType.INSTANCE;
            }
            return new FunctionType(returnType, paramTypes);
        }

        @Override
        public Type visitFormalParameter(FormalParameterContext ctx) {
            if (ctx.typeLiteral() != null) {
                return visit(ctx.typeLiteral());
            }
            if (ctx.defaultValue() != null) {
                return visit(ctx.defaultValue());
            }
            FormalParameterListContext parameterList = (FormalParameterListContext) ctx.getParent();
            FunctionExprContext functionExpr = ((FunctionExprContext) parameterList.getParent());
            int argumentIndex = parameterList.formalParameter().indexOf(ctx);
            Type type = visit(functionExpr);
            if (type instanceof FunctionType functionType) {
                return functionType.parameterTypes().get(argumentIndex);
            } else if (type instanceof ClassType classType) {
                return Symbols.getAnonymousFunction(classType, unit.getEnv())
                        .map(it -> it.getParameterList().get(argumentIndex))
                        .map(ParameterSymbol::getType)
                        .orElse(AnyType.INSTANCE);
            }
            return AnyType.INSTANCE;
        }

        @Override
        public Type visitDefaultValue(DefaultValueContext ctx) {
            return visit(ctx.expression());
        }

        @Override
        public Type visitReturnType(ReturnTypeContext ctx) {
            return visit(ctx.typeLiteral());
        }

        @Override
        public Type visitClassDeclaration(ClassDeclarationContext ctx) {
            return unit.getSymbol(ctx, ClassSymbol.class)
                    .map(Symbol::getType)
                    .orElse(AnyType.INSTANCE);
        }

        @Override
        public Type visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
            List<Type> paramTypes = toTypeList(ctx.formalParameterList());
            Type returnType;
            if (unit.getSymbol(ctx).orElse(null) instanceof ConstructorSymbol symbol) {
                returnType = symbol.getDeclaringClass().getType();
            } else {
                returnType = AnyType.INSTANCE;
            }
            return new FunctionType(returnType, paramTypes);
        }

        @Override
        public Type visitVariableDeclaration(VariableDeclarationContext ctx) {
            if (ctx.typeLiteral() != null) {
                return visit(ctx.typeLiteral());
            } else {
                return visit(ctx.initializer());
            }
        }

        @Override
        public Type visitInitializer(InitializerContext ctx) {
            return visit(ctx.expression());
        }

        @Override
        public Type visitOperatorFunctionDeclaration(OperatorFunctionDeclarationContext ctx) {
            List<Type> paramTypes = toTypeList(ctx.formalParameterList());
            Type returnType = visit(ctx.returnType());
            return new FunctionType(returnType, paramTypes);
        }

        @Override
        public Type visitForeachVariable(ForeachVariableContext ctx) {
            // variable -> variableList -> forEach
            ForeachStatementContext forEachStatement = (ForeachStatementContext) ctx.getParent().getParent();
            List<ForeachVariableContext> variables = forEachStatement.foreachVariableList().foreachVariable();
            Type iterableType = visit(forEachStatement.expression());
            Type result = Operators.apply(iterableType, Operator.FOR_IN, unit.getEnv()).orElse(null);
            if (result instanceof ListType listType) {
                return getListForeachVariableType(listType.elementType(), ctx, variables);
            }
            if (result instanceof MapType mapType) {
                return getMapForeachVariableType(mapType, ctx, variables);
            }
            return AnyType.INSTANCE;
        }

        @Override
        public Type visitAssignmentExpr(AssignmentExprContext ctx) {
            return visit(ctx.left);
        }

        @Override
        public Type visitThisExpr(ThisExprContext ctx) {
            return lookupLocalSymbol(ctx, "this")
                    .map(Symbol::getType)
                    .orElse(AnyType.INSTANCE);
        }

        @Override
        public Type visitMapLiteralExpr(MapLiteralExprContext ctx) {
            Optional<MapEntryContext> firstEntry = Optional.ofNullable(ctx.mapEntryList())
                    .map(entries -> entries.mapEntry(0));
            Type keyType = firstEntry.map(entry -> visit(entry.key)).orElse(AnyType.INSTANCE);
            Type valueType = firstEntry.map(entry -> visit(entry.value)).orElse(AnyType.INSTANCE);
            return new MapType(keyType, valueType);
        }

        @Override
        public Type visitIntRangeExpr(IntRangeExprContext ctx) {
            return Operators.apply(visit(ctx.from), visit(ctx.to), Operator.RANGE, unit.getEnv()).orElse(AnyType.INSTANCE);
        }

        @Override
        public Type visitSimpleNameExpr(SimpleNameExprContext ctx) {
            return lookupSymbol(ctx, ctx.getText())
                    .map(Symbol::getType)
                    .orElse(AnyType.INSTANCE);
        }

        @Override
        public Type visitBinaryExpr(BinaryExprContext ctx) {
            String literal = ctx.op.getText();
            Type leftType = visit(ctx.left);
            Type rightType = visit(ctx.right);
            return Operator.of(literal, Operator.Kind.BINARY)
                    .flatMap(op -> Operators.apply(leftType, rightType, op, unit.getEnv()))
                    .orElse(leftType);
        }

        @Override
        public Type visitParensExpr(ParensExprContext ctx) {
            return visit(ctx.expression());
        }

        @Override
        public Type visitTypeCastExpr(TypeCastExprContext ctx) {
            return visit(ctx.typeLiteral());
        }

        @Override
        public Type visitFunctionExpr(FunctionExprContext ctx) {
            if (ctx.typeLiteral() != null) {
                return visit(ctx.typeLiteral());
            } else {
                // functionExpr -> assignExpr|callExpr
                ParserRuleContext caller = ctx.getParent();
                if (caller instanceof AssignmentExprContext) {
                    Type leftType = visit(caller);
                    if (leftType != null) {
                        return leftType;
                    }
                } else if (caller.getParent() instanceof CallExprContext callExpr) {
                    ExpressionContext expression = callExpr.expression();
                    if (expression instanceof MemberAccessExprContext memberAccessExpr) {
                        List<Type> argumentTypes = new ArrayList<>();
                        List<ExpressionContext> callExpressions = callExpr.expressionList().expression();
                        int functionExprPosition = callExpressions.indexOf(ctx);
                        for (int i = 0; i < functionExprPosition; i++) {
                            Type argumentType = visit(callExpressions.get(i));
                            if (argumentType == null) {
                                argumentType = AnyType.INSTANCE;
                            }
                            argumentTypes.add(argumentType);
                        }
                        Type type = visit(memberAccessExpr.expression());
                        String name = memberAccessExpr.simpleName().getText();
                        List<Executable> functions = Symbols.getExecutableMembersByName(type, name, unit.getEnv()).toList();
                        return Executables.predictNextArgumentType(functions, argumentTypes, unit.getEnv());
                    }
                }
            }
            List<Type> paramTypes = new ArrayList<>();
            for (int i = 0; i < ctx.formalParameterList().formalParameter().size(); i++) {
                paramTypes.add(AnyType.INSTANCE);
            }
            return new FunctionType(AnyType.INSTANCE, paramTypes);
        }

        @Override
        public Type visitBracketHandlerExpr(BracketHandlerExprContext ctx) {
            // FIXME
            return AnyType.INSTANCE;
        }

        @Override
        public Type visitUnaryExpr(UnaryExprContext ctx) {
            Type type = visit(ctx.expression());
            return Operator.of(ctx.op.getText(), Operator.Kind.UNARY)
                    .flatMap(op -> Operators.apply(type, op, unit.getEnv()))
                    .orElse(AnyType.INSTANCE);
        }

        @Override
        public Type visitTernaryExpr(TernaryExprContext ctx) {
            return visit(ctx.truePart);
        }

        @Override
        public Type visitLiteralExpr(LiteralExprContext ctx) {
            return switch (CSTNodes.getTokenType(ctx.literal)) {
                case DECIMAL_LITERAL -> {
                    String literal = ctx.literal.getText();
                    char last = literal.charAt(literal.length() - 1);
                    yield switch (last) {
                        case 'l', 'L' -> LongType.INSTANCE;
                        default -> IntType.INSTANCE;
                    };
                }
                case FLOAT_LITERAL -> {
                    String literal = ctx.literal.getText();
                    char last = literal.charAt(literal.length() - 1);
                    yield switch (last) {
                        case 'f', 'F' -> FloatType.INSTANCE;
                        default -> DoubleType.INSTANCE;
                    };
                }
                case HEX_LITERAL -> IntType.INSTANCE;
                case STRING_LITERAL -> StringType.INSTANCE;
                case TRUE, FALSE -> BoolType.INSTANCE;
                case NULL -> AnyType.INSTANCE;
                default -> null;
            };
        }

        @Override
        public Type visitLogicExpr(LogicExprContext ctx) {
            return visit(ctx.left);
        }

        @Override
        public Type visitMemberAccessExpr(MemberAccessExprContext ctx) {
            Type leftType = visit(ctx.expression());
            if (!(leftType instanceof SymbolProvider provider)) {
                return AnyType.INSTANCE;
            }
            if (ctx.simpleName() == null) {
                return AnyType.INSTANCE;
            }
            String simpleName = ctx.simpleName().getText();
            for (Symbol member : provider.withExpands(unit.getEnv()).getSymbols()) {
                if (member.getName().equals(simpleName)) {
                    return member.getType();
                }
            }
            return Operators.apply(leftType, StringType.INSTANCE, Operator.MEMBER_GET, unit.getEnv()).orElse(AnyType.INSTANCE);
        }

        @Override
        public Type visitArrayLiteralExpr(ArrayLiteralExprContext ctx) {
            Type firstElementType = visit(ctx.expressionList().expression(0));
            if (firstElementType == null) {
                firstElementType = AnyType.INSTANCE;
            }
            return new ArrayType(firstElementType);
        }

        @Override
        public Type visitCallExpr(CallExprContext ctx) {
            if (ctx.expression() instanceof MemberAccessExprContext memberAccessExpr) {
                Type owner = visit(memberAccessExpr.expression());
                if (owner == null) {
                    return null;
                }
                List<Type> argumentTypes = new ArrayList<>();
                for (ExpressionContext expressionContext : ctx.expressionList().expression()) {
                    Type argumentType = visit(expressionContext);
                    if (argumentType == null) {
                        argumentType = AnyType.INSTANCE;
                    }
                    argumentTypes.add(argumentType);
                }
                List<Executable> functions = Symbols.getExecutableMembersByName(owner, memberAccessExpr.simpleName().getText(), unit.getEnv()).toList();
                Executable matchedFunction = Executables.findBestMatch(functions, argumentTypes, unit.getEnv());
                return matchedFunction == null ? null : matchedFunction.getReturnType();
            } else {
                Type leftType = visit(ctx.expression());
                if (leftType instanceof FunctionType functionType) {
                    return functionType.returnType();
                } else if (leftType instanceof ClassType) {
                    return leftType;
                } else {
                    return null;
                }
            }
        }

        @Override
        public Type visitMemberIndexExpr(MemberIndexExprContext ctx) {
            Type leftType = visit(ctx.left);
            Type rightType = visit(ctx.index);
            return Operators.apply(leftType, rightType, Operator.INDEX_GET, unit.getEnv())
                    .orElse(AnyType.INSTANCE);
        }

        @Override
        public Type visitCompareExpr(CompareExprContext ctx) {
            Type leftType = visit(ctx.left);
            Type rightType = visit(ctx.right);
            return Operator.of(ctx.op.getText(), Operator.Kind.BINARY)
                    .flatMap(op -> Operators.apply(leftType, rightType, op, unit.getEnv()))
                    .orElse(AnyType.INSTANCE);
        }

        @Override
        public Type visitArrayType(ArrayTypeContext ctx) {
            Type elementType = visit(ctx.typeLiteral());
            return new ArrayType(elementType);
        }

        @Override
        public Type visitIntersectionType(IntersectionTypeContext ctx) {
            List<Type> types = ctx.typeLiteral().stream()
                    .map(this::visit)
                    .toList();
            return new IntersectionType(types);
        }

        @Override
        public Type visitMapType(MapTypeContext ctx) {
            Type keyType = visit(ctx.key);
            Type valueType = visit(ctx.value);
            return new MapType(keyType, valueType);
        }

        @Override
        public Type visitFunctionType(FunctionTypeContext ctx) {
            List<Type> paramTypes = toTypeList(ctx.typeLiteralList());
            Type returnType = visitReturnType(ctx.returnType());
            return new FunctionType(returnType, paramTypes);
        }

        @Override
        public Type visitListType(ListTypeContext ctx) {
            Type elementType = visit(ctx.typeLiteral());
            return new ListType(elementType);
        }

        @Override
        public Type visitPrimitiveType(PrimitiveTypeContext ctx) {
            return switch (CSTNodes.getTokenType(ctx.start)) {
                case ANY -> AnyType.INSTANCE;
                case BYTE -> ByteType.INSTANCE;
                case SHORT -> ShortType.INSTANCE;
                case INT -> IntType.INSTANCE;
                case LONG -> LongType.INSTANCE;
                case FLOAT -> FloatType.INSTANCE;
                case DOUBLE -> DoubleType.INSTANCE;
                case BOOL -> BoolType.INSTANCE;
                case VOID -> VoidType.INSTANCE;
                case STRING -> StringType.INSTANCE;
                default -> null;
            };
        }

        @Override
        public Type visitClassType(ClassTypeContext ctx) {
            return SymbolResolver.lookupClass(ctx.qualifiedName(), unit).stream()
                    .findFirst()
                    .map(Symbol::getType)
                    .orElse(AnyType.INSTANCE);
        }

        @Override
        public Type visit(ParseTree node) {
            if (node != null) {
                return node.accept(this);
            } else {
                return null;
            }
        }

        @Override
        public Type visitChildren(RuleNode node) {
            return null;
        }

        Optional<Symbol> lookupSymbol(ParseTree cst, String simpleName) {
            return lookupLocalSymbol(cst, simpleName)
                    .or(() -> lookupGlobalSymbol(simpleName));
        }

        Optional<Symbol> lookupLocalSymbol(ParseTree cst, String simpleName) {
            return Compilations.lookupScope(unit, cst)
                    .map(scope -> scope.lookupSymbol(simpleName));
        }

        Optional<Symbol> lookupGlobalSymbol(String simpleName) {
            return unit.getEnv().getGlobals()
                    .filter(symbol -> symbol.getName().equals(simpleName))
                    .findFirst();
        }

        List<Type> toTypeList(FormalParameterListContext ctx) {
            return ctx.formalParameter().stream().map(this::visit).toList();
        }

        List<Type> toTypeList(TypeLiteralListContext ctx) {
            return ctx.typeLiteral().stream().map(this::visit).toList();
        }

        Type getListForeachVariableType(Type elementType, ForeachVariableContext variable, List<ForeachVariableContext> variables) {
            int total = variables.size();
            int index = variables.indexOf(variable);
            if (total == 1) {
                return elementType;
            }
            if (total == 2) {
                return index == 0 ? IntType.INSTANCE : elementType;
            }
            return null;
        }

        Type getMapForeachVariableType(MapType mapType, ForeachVariableContext variable, List<ForeachVariableContext> variables) {
            int total = variables.size();
            int index = variables.indexOf(variable);
            if (total == 1) {
                return mapType.keyType();
            }
            if (total == 2) {
                return index == 0 ? mapType.keyType() : mapType.valueType();
            }
            return null;
        }
    }

}
