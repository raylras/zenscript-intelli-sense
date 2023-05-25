package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.*;

import java.util.Objects;

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
        return new AnyType();
    }

    @Override
    public Type visitCallExpr(CallExprContext ctx) {
        Type leftType = ctx.Left.accept(this);
        if (leftType instanceof FunctionType) {
            return  ((FunctionType) leftType).returnType;
        }
        return null;
    }

    @Override
    public Type visitMemberAccessExpr(MemberAccessExprContext ctx) {
        Type leftType = ctx.Left.accept(this);
        Symbol symbol = leftType.lookupSymbol();
        if (symbol != null) {
            for (Symbol member : symbol.getMembers()) {
                if (Objects.equals(member.getName(), ctx.simpleName().getText())) {
                    return member.getType();
                }
            }
        }
        return leftType;
    }

    @Override
    public Type visitTypeCastExpr(TypeCastExprContext ctx) {
        return new LiteralTypeResolver(unit).resolve(ctx.typeLiteral());
    }

    @Override
    public Type visitTrueLiteralExpr(TrueLiteralExprContext ctx) {
        return new BoolType();
    }

    @Override
    public Type visitFalseLiteralExpr(FalseLiteralExprContext ctx) {
        return new BoolType();
    }

    @Override
    public Type visitStringLiteralExpr(StringLiteralExprContext ctx) {
        return new StringType();
    }

    public Type visitTypeLiteral(TypeLiteralContext ctx) {
        return new LiteralTypeResolver(unit).resolve(ctx);
    }

}
