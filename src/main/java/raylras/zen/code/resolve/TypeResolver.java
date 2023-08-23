package raylras.zen.code.resolve;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.*;
import raylras.zen.code.type.*;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Functions;
import raylras.zen.util.Symbols;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class TypeResolver {

    private TypeResolver() {}

    public static Type getType(ParseTree cst, CompilationUnit unit) {
        Objects.requireNonNull(cst);
        Objects.requireNonNull(unit);
        Type type = cst.accept(new TypeVisitor(unit));
        return type == null ? AnyType.INSTANCE : type;
    }

    private static final class TypeVisitor extends Visitor<Type> {
        private final CompilationUnit unit;

        public TypeVisitor(CompilationUnit unit) {
            this.unit = unit;
        }

        private List<Type> toTypeList(FormalParameterListContext ctx) {
            return ctx.formalParameter().stream()
                    .map(this::visit)
                    .collect(Collectors.toList());
        }

        private List<Type> toTypeList(TypeLiteralListContext ctx) {
            return ctx.typeLiteral().stream()
                    .map(this::visit)
                    .collect(Collectors.toList());
        }

        private Symbol lookupSymbol(ParseTree cst, String simpleName) {
            Scope scope = unit.lookupScope(cst);
            Symbol symbol = null;
            if (scope != null) {
                symbol = scope.lookupSymbol(simpleName);
            }
            if (symbol == null) {
                for (Symbol globalSymbol : unit.getEnv().getGlobalSymbols()) {
                    if (simpleName.equals(globalSymbol.getName())) {
                        symbol = globalSymbol;
                    }
                }
            }
            return symbol;
        }

        @Override
        public Type visitImportDeclaration(ImportDeclarationContext ctx) {
            ImportSymbol symbol = unit.getSymbol(ctx, ImportSymbol.class);
            if (symbol != null) {
                return symbol.getType();
            } else {
                return AnyType.INSTANCE;
            }
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
            Type functionType = visit(functionExpr);
            int argumentIndex = parameterList.formalParameter().indexOf(ctx);
            if (functionType instanceof FunctionType) {
                return ((FunctionType) functionType).getParameterTypes().get(argumentIndex);
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
            ClassSymbol symbol = unit.getSymbol(ctx, ClassSymbol.class);
            if (symbol != null) {
                return symbol.getType();
            } else {
                return AnyType.INSTANCE;
            }
        }

        @Override
        public Type visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
            List<Type> paramTypes = toTypeList(ctx.formalParameterList());
            // FIXME: should be zen class type
            Type returnType = AnyType.INSTANCE;
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
            Type returnType;
            if (ctx.typeLiteral().size() == 1) {
                returnType = visit(ctx.typeLiteral(0));
            } else {
                List<Type> typeList = ctx.typeLiteral().stream()
                        .map(this::visit)
                        .collect(Collectors.toList());
                returnType = new UnionType(typeList);
            }
            return new FunctionType(returnType, paramTypes);
        }

        @Override
        public Type visitForeachVariable(ForeachVariableContext ctx) {
            // variable -> variableList -> forEach
            ForeachStatementContext forEachStatement = (ForeachStatementContext) ctx.getParent().getParent();
            Type iterableType = visit(forEachStatement.expression());
            if (iterableType == IntRangeType.INSTANCE) {
                return IntType.INSTANCE;
            }
            if (iterableType instanceof ListType) {
                return ((ListType) iterableType).getElementType();
            }
            if (iterableType instanceof ArrayType) {
                return ((ArrayType) iterableType).getElementType();
            }
            if (iterableType instanceof MapType) {
                MapType mapType = (MapType) iterableType;
                List<ForeachVariableContext> variables = forEachStatement.foreachVariableList().foreachVariable();
                if (variables.size() == 1) {
                    return mapType.getKeyType();
                } else if (variables.size() == 2) {
                    if (variables.get(0) == ctx) {
                        return mapType.getKeyType();
                    }
                    if (variables.get(1) == ctx) {
                        return mapType.getValueType();
                    }
                }
            }
            return AnyType.INSTANCE;
        }

        @Override
        public Type visitAssignmentExpr(AssignmentExprContext ctx) {
            return visit(ctx.left);
        }

        @Override
        public Type visitThisExpr(ThisExprContext ctx) {
            // FIXME: inferring the type of this expression
            return AnyType.INSTANCE;
        }

        @Override
        public Type visitMapLiteralExpr(MapLiteralExprContext ctx) {
            if (ctx.mapEntryList() == null) {
                return new MapType(AnyType.INSTANCE, AnyType.INSTANCE);
            }
            MapEntryContext firstEntry = ctx.mapEntryList().mapEntry(0);
            Type keyType = visit(firstEntry.key);
            Type valueType = visit(firstEntry.value);
            return new MapType(keyType, valueType);
        }

        @Override
        public Type visitIntRangeExpr(IntRangeExprContext ctx) {
            return IntRangeType.INSTANCE;
        }

        @Override
        public Type visitSimpleNameExpr(SimpleNameExprContext ctx) {
            Symbol symbol = lookupSymbol(ctx, ctx.simpleName().getText());
            if (symbol != null) {
                return symbol.getType();
            } else {
                return AnyType.INSTANCE;
            }
        }

        @Override
        public Type visitBinaryExpr(BinaryExprContext ctx) {
            return visit(ctx.left);
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
                } else if (caller.getParent() instanceof CallExprContext) {
                    CallExprContext callExpr = (CallExprContext) caller.getParent();
                    ExpressionContext expression = callExpr.expression();
                    if (expression instanceof MemberAccessExprContext) {
                        MemberAccessExprContext memberAccessExpr = (MemberAccessExprContext) expression;
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
                        List<FunctionSymbol> functions = Symbols.getMembersByName(type, name, FunctionSymbol.class);
                        return Functions.predictNextArgumentType(functions, argumentTypes);
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
            return unit.getEnv().getBracketHandlerManager().getType(ctx.raw().getText(), unit.getEnv());
        }

        @Override
        public Type visitUnaryExpr(UnaryExprContext ctx) {
            return visit(ctx.expression());
        }

        @Override
        public Type visitTernaryExpr(TernaryExprContext ctx) {
            return visit(ctx.truePart);
        }

        @Override
        public Type visitLiteralExpr(LiteralExprContext ctx) {
            switch (CSTNodes.getTokenType(ctx.start)) {
                case ZenScriptLexer.INT_LITERAL:
                    return IntType.INSTANCE;

                case ZenScriptLexer.LONG_LITERAL:
                    return LongType.INSTANCE;

                case ZenScriptLexer.FLOAT_LITERAL:
                    return FloatType.INSTANCE;

                case ZenScriptLexer.DOUBLE_LITERAL:
                    return DoubleType.INSTANCE;

                case ZenScriptLexer.STRING_LITERAL:
                    return StringType.INSTANCE;

                case ZenScriptLexer.TRUE_LITERAL:
                case ZenScriptLexer.FALSE_LITERAL:
                    return BoolType.INSTANCE;

                case ZenScriptLexer.NULL_LITERAL:
                    return AnyType.INSTANCE;

                default:
                    return null;
            }
        }

        @Override
        public Type visitMemberAccessExpr(MemberAccessExprContext ctx) {
            Type leftType = visit(ctx.expression());
            if (leftType == null) {
                return null;
            }
            String simpleName = ctx.simpleName().getText();
            for (Symbol member : leftType.getMembers()) {
                if (Objects.equals(member.getName(), simpleName)) {
                    return member.getType();
                }
            }
            return leftType;
        }

        @Override
        public Type visitArrayLiteralExpr(ArrayLiteralExprContext ctx) {
            Type firstElementType = visit(ctx.expressionList().expression(0));
            if (firstElementType != null) {
                return new ArrayType(firstElementType);
            } else {
                return new ArrayType(AnyType.INSTANCE);
            }
        }

        @Override
        public Type visitCallExpr(CallExprContext ctx) {
            if (ctx.expression() instanceof MemberAccessExprContext) {
                MemberAccessExprContext memberAccessExpr = (MemberAccessExprContext) ctx.expression();
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
                List<FunctionSymbol> functions = Symbols.getMembersByName(owner, memberAccessExpr.simpleName().getText(), FunctionSymbol.class);
                FunctionSymbol matchedFunction = Functions.findBestMatch(functions, argumentTypes);
                return matchedFunction == null ? null : matchedFunction.getReturnType();
            } else {
                Type leftType = visit(ctx.expression());
                if (leftType instanceof FunctionType) {
                    return ((FunctionType) leftType).getReturnType();
                } else {
                    return null;
                }
            }
        }

        @Override
        public Type visitMemberIndexExpr(MemberIndexExprContext ctx) {
            Type leftType = visit(ctx.left);
            if (leftType instanceof ArrayType) {
                return ((ArrayType) leftType).getElementType();
            }
            if (leftType instanceof ListType) {
                return ((ListType) leftType).getElementType();
            }
            if (leftType instanceof MapType) {
                return ((MapType) leftType).getValueType();
            }
            return null;
        }

        @Override
        public Type visitArrayType(ArrayTypeContext ctx) {
            Type elementType = visit(ctx.typeLiteral());
            return new ArrayType(elementType);
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
            switch (CSTNodes.getTokenType(ctx.start)) {
                case ZenScriptLexer.ANY:
                    return AnyType.INSTANCE;

                case ZenScriptLexer.BYTE:
                    return ByteType.INSTANCE;

                case ZenScriptLexer.SHORT:
                    return ShortType.INSTANCE;

                case ZenScriptLexer.INT:
                    return IntType.INSTANCE;

                case ZenScriptLexer.LONG:
                    return LongType.INSTANCE;

                case ZenScriptLexer.FLOAT:
                    return FloatType.INSTANCE;

                case ZenScriptLexer.DOUBLE:
                    return DoubleType.INSTANCE;

                case ZenScriptLexer.BOOL:
                    return BoolType.INSTANCE;

                case ZenScriptLexer.VOID:
                    return VoidType.INSTANCE;

                case ZenScriptLexer.STRING:
                    return StringType.INSTANCE;

                default:
                    return null;
            }
        }

        @Override
        public Type visitClassType(ClassTypeContext ctx) {
            Scope scope = unit.getScope(unit.getParseTree());
            String qualifiedName = ctx.qualifiedName().getText();
            Symbol symbol = scope.lookupSymbol(qualifiedName);
            if (symbol != null) {
                return symbol.getType();
            } else {
                return null;
            }
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
    }

}
