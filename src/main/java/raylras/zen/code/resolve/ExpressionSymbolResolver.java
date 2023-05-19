package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.*;

import java.util.List;

public class ExpressionSymbolResolver extends Visitor<Symbol> {

    private final CompilationUnit unit;

    public ExpressionSymbolResolver(CompilationUnit unit) {
        this.unit = unit;
    }

    public Symbol resolve(ParseTree node) {
        if (node == null)
            return null;
        return node.accept(this);
    }

    @Override
    public Symbol visitLocalAccessExpr(ZenScriptParser.LocalAccessExprContext ctx) {
        return unit.lookupSymbol(ctx);
    }

    @Override
    public Symbol visitCallExpr(ZenScriptParser.CallExprContext ctx) {
        Symbol leftSymbol = ctx.Left.accept(this);
        if (leftSymbol == null)
            return null;
        Type leftType = leftSymbol.getType();
        if (leftType == null)
            return null;
        return leftType.lookupSymbol(unit);
    }

    @Override
    public Symbol visitMemberAccessExpr(ZenScriptParser.MemberAccessExprContext ctx) {
        Symbol leftSymbol = ctx.Left.accept(this);
        if (leftSymbol == null)
            return null;

        Symbol leftSymbolType = leftSymbol.getType().lookupSymbol(unit);
        if (leftSymbolType == null) {
            return null;
        }

        String simpleName = new NameResolver().resolve(ctx.simpleName());
        if (simpleName == null)
            return leftSymbol;

        for (Symbol member : leftSymbolType.getMembers()) {
            if (simpleName.equals(member.getName())) {
                return member;
            }
        }

        return leftSymbol;
    }

    @Override
    public Symbol visitTypeCastExpr(ZenScriptParser.TypeCastExprContext ctx) {
        Type type = new LiteralTypeResolver(unit).resolve(ctx.typeLiteral());
        if (type == null)
            return null;
        return type.lookupSymbol(unit);
    }

    @Override
    public Symbol visitTrueLiteralExpr(ZenScriptParser.TrueLiteralExprContext ctx) {
        return BoolType.INSTANCE.lookupSymbol(unit);
    }

    @Override
    public Symbol visitFalseLiteralExpr(ZenScriptParser.FalseLiteralExprContext ctx) {
        return BoolType.INSTANCE.lookupSymbol(unit);
    }

    @Override
    public Symbol visitStringLiteralExpr(ZenScriptParser.StringLiteralExprContext ctx) {
        return StringType.INSTANCE.lookupSymbol(unit);
    }

    @Override
    public Symbol visitFloatLiteralExpr(ZenScriptParser.FloatLiteralExprContext ctx) {
        return FloatType.INSTANCE.lookupSymbol(unit);
    }

    @Override
    public Symbol visitLongLiteralExpr(ZenScriptParser.LongLiteralExprContext ctx) {
        return LongType.INSTANCE.lookupSymbol(unit);
    }

    @Override
    public Symbol visitNullLiteralExpr(ZenScriptParser.NullLiteralExprContext ctx) {
        return AnyType.INSTANCE.lookupSymbol(unit);
    }

    @Override
    public Symbol visitIntLiteralExpr(ZenScriptParser.IntLiteralExprContext ctx) {
        return IntType.INSTANCE.lookupSymbol(unit);
    }

    @Override
    public Symbol visitDoubleLiteralExpr(ZenScriptParser.DoubleLiteralExprContext ctx) {
        return DoubleType.INSTANCE.lookupSymbol(unit);
    }

    @Override
    public Symbol visitParensExpr(ZenScriptParser.ParensExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Symbol visitTypeLiteral(ZenScriptParser.TypeLiteralContext ctx) {
        Type type = new LiteralTypeResolver(unit).resolve(ctx);
        if (type == null)
            return null;
        return type.lookupSymbol(unit);
    }

}
