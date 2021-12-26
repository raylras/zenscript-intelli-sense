package raylras.zen.symbol;

import org.antlr.v4.runtime.Token;
import raylras.zen.lsp.antlr.ZenScriptParser;
import raylras.zen.scope.Scope;
import raylras.zen.type.Type;
import raylras.zen.type.TypeZenClass;

// ZenClass and JavaZenClass

public class SymbolClass extends SymbolBase<TypeZenClass> implements Type {

    public SymbolClass(Scope parent, Token token) {
        super(parent, new TypeZenClass((ZenScriptParser.TypeClassContext) null), token);
        System.out.println(super.token);
    }

    @Override
    public String toString() {
        return getSymbolName();
    }

}
