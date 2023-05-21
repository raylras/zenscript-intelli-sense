package raylras.zen.code.type.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.*;

// 暂时不知道有什么用，可以直接 unit.lookupSymbol
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
    public Symbol visitMemberAccessExpr(ZenScriptParser.MemberAccessExprContext ctx) {
        Symbol leftSymbol = ctx.Left.accept(this);
        if (leftSymbol == null)
            return null;

        Type leftSymbolType = leftSymbol.getType();
        if (leftSymbolType == null || leftSymbolType.getKind() != Type.Kind.CLASS) {
            return null;
        }

        String simpleName = new NameResolver().resolve(ctx.simpleName());
        if (simpleName == null)
            return leftSymbol;

        for (Symbol member : unit.typeService().getAllMembers(((ClassType) leftSymbolType).getSymbol())) {
            if (simpleName.equals(member.getName())) {
                return member;
            }
        }

        return leftSymbol;
    }

//    @Override
//    public Symbol visitTypeCastExpr(ZenScriptParser.TypeCastExprContext ctx) {
//        Type type = new LiteralTypeResolver(unit).resolve(ctx.typeLiteral());
//        if (type == null)
//            return null;
//        return type.lookupSymbol(unit);
//    }
//
//    @Override
//    public Symbol visitTypeLiteral(ZenScriptParser.TypeLiteralContext ctx) {
//        Type type = new LiteralTypeResolver(unit).resolve(ctx);
//        if (type == null)
//            return null;
//        return type.lookupSymbol(unit);
//    }

}
