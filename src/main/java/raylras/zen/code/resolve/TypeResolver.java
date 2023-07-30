package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.ImportSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class TypeResolver {

    private TypeResolver() {}

    public static Type getType(ParseTree cst, CompilationUnit unit) {
        Objects.requireNonNull(cst);
        Objects.requireNonNull(unit);
        return cst.accept(new TypeVisitor(unit));
    }

    private static final class TypeVisitor extends Visitor<Type> {
        private final CompilationUnit unit;

        public TypeVisitor(CompilationUnit unit) {
            this.unit = unit;
        }

        private List<Type> getParameterTypeList(List<ParameterContext> params) {
            return params.stream().map(this::visit).collect(Collectors.toList());
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

        @Override
        public Type visitImportDeclaration(ImportDeclarationContext ctx) {
            ImportSymbol symbol = unit.getSymbol(ctx, ImportSymbol.class);
            if (symbol != null) {
                return symbol.getType();
            } else {
                return null;
            }
        }

        @Override
        public Type visitFunctionDeclaration(FunctionDeclarationContext ctx) {
            List<Type> paramTypes = getParameterTypeList(ctx.parameter());
            Type returnType = visit(ctx.returnType());
            if (returnType != null) {
                return new FunctionType(returnType, paramTypes);
            } else {
                return new FunctionType(AnyType.INSTANCE, paramTypes);
            }
        }

        @Override
        public Type visitExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
            List<Type> paramTypes = getParameterTypeList(ctx.parameter());
            Type returnType = visit(ctx.returnType());
            if (returnType != null) {
                return new FunctionType(returnType, paramTypes);
            } else {
                return new FunctionType(AnyType.INSTANCE, paramTypes);
            }
        }

        @Override
        public Type visitParameter(ParameterContext ctx) {
            Type declaredType = visit(ctx.typeLiteral());
            if (declaredType != null) {
                return declaredType;
            } else {
                Type exprType = visit(ctx.defaultValue());
                if (exprType != null) {
                    return exprType;
                }
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
                return null;
            }
        }

        @Override
        public Type visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
            List<Type> paramTypes = getParameterTypeList(ctx.parameter());
            // FIXME: should be zen class type
            Type returnType = AnyType.INSTANCE;
            return new FunctionType(returnType, paramTypes);
        }

        @Override
        public Type visitVariableDeclaration(VariableDeclarationContext ctx) {
            Type declaredType = visit(ctx.typeLiteral());
            if (declaredType != null) {
                return declaredType;
            } else {
                return visit(ctx.initializer());
            }
        }

        @Override
        public Type visitInitializer(InitializerContext ctx) {
            return visit(ctx.expression());
        }

        @Override
        public Type visitForeachVariableDeclaration(ForeachVariableDeclarationContext ctx) {
            // FIXME: inferring the type of foreach variable
            return AnyType.INSTANCE;
        }

        @Override
        public Type visitTrueLiteralExpr(TrueLiteralExprContext ctx) {
            return BoolType.INSTANCE;
        }

        @Override
        public Type visitFalseLiteralExpr(FalseLiteralExprContext ctx) {
            return BoolType.INSTANCE;
        }

        @Override
        public Type visitStringLiteralExpr(StringLiteralExprContext ctx) {
            return StringType.INSTANCE;
        }

        @Override
        public Type visitIntRangeExpr(IntRangeExprContext ctx) {
            return IntRangeType.INSTANCE;
        }

        @Override
        public Type visitArrayInitializerExpr(ArrayInitializerExprContext ctx) {
            Type firstElementType = visit(ctx.expression(0));
            if (firstElementType != null) {
                return new ArrayType(firstElementType);
            } else {
                return new ArrayType(AnyType.INSTANCE);
            }
        }

        @Override
        public Type visitFloatLiteralExpr(FloatLiteralExprContext ctx) {
            return FloatType.INSTANCE;
        }

        @Override
        public Type visitLongLiteralExpr(LongLiteralExprContext ctx) {
            return LongType.INSTANCE;
        }

        @Override
        public Type visitLocalAccessExpr(LocalAccessExprContext ctx) {
            Scope scope = unit.lookupScope(ctx);
            if (scope != null) {
                String identifier = ctx.identifier().getText();
                Symbol symbol = scope.lookupSymbol(identifier);
                if (symbol != null) {
                    return symbol.getType();
                }
            }
            return null;
        }

        @Override
        public Type visitCallExpr(CallExprContext ctx) {
            // FIXME: overloaded functions
            Type leftType = visit(ctx.Left);
            if (leftType instanceof FunctionType) {
                return ((FunctionType) leftType).getReturnType();
            } else {
                return null;
            }
        }

        @Override
        public Type visitBracketHandlerExpr(BracketHandlerExprContext ctx) {
            // FIXME: inferring the type of bracket handler expression
            return AnyType.INSTANCE;
        }

        @Override
        public Type visitThisExpr(ThisExprContext ctx) {
            // FIXME: inferring the type of this expression
            return AnyType.INSTANCE;
        }

        @Override
        public Type visitTernaryExpr(TernaryExprContext ctx) {
            return visit(ctx.TruePart);
        }

        @Override
        public Type visitFunctionExpr(FunctionExprContext ctx) {
            Type declaredType = visit(ctx.typeLiteral());
            if (declaredType != null) {
                return declaredType;
            } else {
                List<Type> paramTypes = getParameterTypeList(ctx.parameter());
                Type returnType = AnyType.INSTANCE;
                return new FunctionType(returnType, paramTypes);
            }
        }

        @Override
        public Type visitMapInitializerExpr(MapInitializerExprContext ctx) {
            MapEntryContext firstEntry = ctx.mapEntry(0);
            if (firstEntry != null) {
                Type keyType = visit(firstEntry.Key);
                Type valueType = visit(firstEntry.Value);
                return new MapType(keyType, valueType);
            } else {
                return new MapType(AnyType.INSTANCE, AnyType.INSTANCE);
            }
        }

        @Override
        public Type visitArrayAccessExpr(ArrayAccessExprContext ctx) {
            Type leftType = visit(ctx.Left);
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
        public Type visitBinaryExpr(BinaryExprContext ctx) {
            return visit(ctx.Left);
        }

        @Override
        public Type visitAssignmentExpr(AssignmentExprContext ctx) {
            return visit(ctx.Left);
        }

        @Override
        public Type visitUnaryExpr(UnaryExprContext ctx) {
            return visit(ctx.expression());
        }

        @Override
        public Type visitNullLiteralExpr(NullLiteralExprContext ctx) {
            return AnyType.INSTANCE;
        }

        @Override
        public Type visitIntLiteralExpr(IntLiteralExprContext ctx) {
            return IntType.INSTANCE;
        }

        @Override
        public Type visitDoubleLiteralExpr(DoubleLiteralExprContext ctx) {
            return DoubleType.INSTANCE;
        }

        @Override
        public Type visitParensExpr(ParensExprContext ctx) {
            return visit(ctx.expression());
        }

        @Override
        public Type visitMemberAccessExpr(MemberAccessExprContext ctx) {
            Type leftType = visit(ctx.Left);
            if (leftType == null) {
                return null;
            }
            String identifier = ctx.identifier().getText();
            for (Symbol member : leftType.getMembers()) {
                if (Objects.equals(member.getSimpleName(), identifier)) {
                    return member.getType();
                }
            }
            return leftType;
        }

        @Override
        public Type visitTypeCastExpr(TypeCastExprContext ctx) {
            return visit(ctx.typeLiteral());
        }

        @Override
        public Type visitArgument(ArgumentContext ctx) {
            return visit(ctx.expression());
        }

        @Override
        public Type visitArrayType(ArrayTypeContext ctx) {
            Type elementType = visit(ctx.typeLiteral());
            return new ArrayType(elementType);
        }

        @Override
        public Type visitFunctionType(FunctionTypeContext ctx) {
            List<Type> paramTypes = ctx.typeLiteral().stream()
                    .map(this::visit)
                    .collect(Collectors.toList());
            Type returnType = visit(ctx.returnType());
            return new FunctionType(returnType, paramTypes);
        }

        @Override
        public Type visitListType(ListTypeContext ctx) {
            Type elementType = visit(ctx.typeLiteral());
            return new ListType(elementType);
        }

        @Override
        public Type visitPrimitiveType(PrimitiveTypeContext ctx) {
            switch (ctx.start.getType()) {
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
            String identifier = ctx.getText();
            Symbol symbol = scope.lookupSymbol(identifier);
            if (symbol != null) {
                return symbol.getType();
            } else {
                return null;
            }
        }

        @Override
        public Type visitMapType(MapTypeContext ctx) {
            Type keyType = visit(ctx.Key);
            Type valueType = visit(ctx.Value);
            return new MapType(keyType, valueType);
        }
    }

}
