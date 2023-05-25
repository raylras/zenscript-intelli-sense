package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.symbol.Declarator;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.*;

public class DeclaratorResolver extends Visitor<Declarator> {

    public Declarator resolve(ParseTree node) {
        if (node == null)
            return null;
        return node.accept(this);
    }

    @Override
    public Declarator visitParameter(ParameterContext ctx) {
        return Declarator.NONE;
    }

    @Override
    public Declarator visitVariableDeclaration(VariableDeclarationContext ctx) {
        switch (ctx.Declarator.getType()) {
            case ZenScriptLexer.VAR:
                return Declarator.VAR;

            case ZenScriptLexer.VAL:
                return Declarator.VAL;

            case ZenScriptLexer.GLOBAL:
                return Declarator.GLOBAL;

            case ZenScriptLexer.STATIC:
                return Declarator.STATIC;

            case  ZenScriptLexer.EXPAND:
                return Declarator.EXPAND;

            default:
                return null;
        }
    }

    @Override
    public Declarator visitSimpleVariable(SimpleVariableContext ctx) {
        return Declarator.NONE;
    }

    @Override
    public Declarator visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        if (ctx.Declarator != null && ctx.Declarator.getType() == ZenScriptLexer.STATIC) {
            return Declarator.STATIC;
        }
        return null;
    }

    @Override
    public Declarator visitExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
        return Declarator.EXPAND;
    }

}
