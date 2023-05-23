package raylras.zen.code.type.resolve;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.*;
import raylras.zen.util.TypeUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpressionTypeResolver extends Visitor<Type> {

    private final CompilationUnit unit;

    public ExpressionTypeResolver(CompilationUnit unit) {
        this.unit = unit;
    }

    public Type resolve(ParseTree node) {
        if (node == null)
            return null;
        return node.accept(this);
    }

    @Override
    public Type visitLocalAccessExpr(LocalAccessExprContext ctx) {
        Scope scope = unit.getScope(ctx);
        String name = NameResolver.resolveName(ctx);
        Symbol symbol = unit.lookupSymbol(Symbol.class, scope, name, true);
        if (symbol != null) {
            return symbol.getType();
        }
        return new ErrorType(name);
    }

    @Override
    public Type visitCallExpr(CallExprContext ctx) {
        // TODO: handle method overrides
        Type leftType = ctx.Left.accept(this);
        if (leftType instanceof FunctionType) {
            return ((FunctionType) leftType).returnType;
        } else if (leftType instanceof ClassType) {
            ClassSymbol classSymbol = ((ClassType) leftType).getSymbol();
            if (classSymbol.isFunctionalInterface()) {
                FunctionType functionType = classSymbol.getFunctionType();
                if (functionType == null) {
                    return null;
                }
                return classSymbol.getFunctionType().returnType;
            }
        }
        return null;
    }

    @Override
    public Type visitThisExpr(ThisExprContext ctx) {
        ParserRuleContext tree = ctx.getParent();
        while (!(tree instanceof ClassDeclarationContext)) {
            if (tree == null) {
                return null;
            }
            tree = tree.getParent();
        }
        Symbol symbol = unit.getSymbol(tree);
        if (symbol instanceof ClassSymbol) {
            return symbol.getType();
        }
        return null;
    }


    @Override
    public Type visitMemberAccessExpr(MemberAccessExprContext ctx) {
        Type leftType = ctx.Left.accept(this);
        if (leftType == null || leftType instanceof ErrorType)
            return null;


        String simpleName = NameResolver.resolveName(ctx.simpleName());
        if (simpleName == null)
            return leftType;

        if (leftType.getKind() == Type.Kind.CLASS) {
            ClassSymbol symbol = ((ClassType) leftType).getSymbol();

            for (Symbol member : unit.typeService().getAllMembers(symbol)) {
                if (Objects.equals(member.getName(), simpleName)) {
                    return member.getType();
                }
            }
        }


        return leftType;
    }

    @Override
    public Type visitArrayIndexExpr(ArrayIndexExprContext ctx) {
        Type leftType = ctx.Left.accept(this);
        if (!TypeUtils.isValidType(leftType)) {
            return null;
        }

        if (leftType.getKind() == Type.Kind.ARRAY) {
            return ((ArrayType) leftType).elementType;
        }


        if (leftType.getKind() == Type.Kind.MAP) {
            return ((MapType) leftType).valueType;
        }

        if (leftType.getKind() == Type.Kind.LIST) {
            return ((ListType) leftType).elementType;
        }

        // TODO: Operator override
        return null;
    }

    @Override
    public Type visitUnaryExpr(UnaryExprContext ctx) {
        return super.visitUnaryExpr(ctx);
        // TODO: Operator override
    }

    @Override
    public Type visitBinaryExpr(BinaryExprContext ctx) {
        return super.visitBinaryExpr(ctx);
        // TODO: Operator override
    }

    @Override
    public Type visitTernaryExpr(TernaryExprContext ctx) {
        return super.visitTernaryExpr(ctx);
        // TODO: commonSuperClass
    }

    @Override
    public Type visitAssignmentExpr(AssignmentExprContext ctx) {
        return ctx.Left.accept(this);
    }

    @Override
    public Type visitIntRangeExpr(IntRangeExprContext ctx) {
        return IntRangeType.INSTANCE;
    }

    @Override
    public Type visitFunctionExpr(FunctionExprContext ctx) {
        LiteralTypeResolver resolver = new LiteralTypeResolver(unit);
        Type returnType = resolver.resolve(ctx.typeLiteral());
        List<Type> parameterTypes = ctx.parameter()
            .stream()
            .map(ParameterContext::typeLiteral)
            .map(resolver::resolve)
            .collect(Collectors.toList());
        return new FunctionType(parameterTypes, returnType);
    }

    @Override
    public Type visitArrayInitializerExpr(ArrayInitializerExprContext ctx) {
        return super.visitArrayInitializerExpr(ctx);
    }

    @Override
    public Type visitMapInitializerExpr(MapInitializerExprContext ctx) {
        return super.visitMapInitializerExpr(ctx);
    }

    @Override
    public Type visitParensExpr(ParensExprContext ctx) {
        return ctx.expression().accept(this);
    }

    @Override
    public Type visitTypeCastExpr(TypeCastExprContext ctx) {
        return new LiteralTypeResolver(unit).resolve(ctx.typeLiteral());
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
    public Type visitTypeLiteral(TypeLiteralContext ctx) {
        return new LiteralTypeResolver(unit).resolve(ctx);
    }


}
