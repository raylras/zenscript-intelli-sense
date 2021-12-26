package raylras.zen.symbol;

import org.antlr.v4.runtime.Token;
import raylras.zen.scope.Scope;
import raylras.zen.scope.ScopeBase;
import raylras.zen.type.Type;

public abstract class SymbolBase<T extends Type> extends ScopeBase implements Symbol<T> {

    protected Token token;
    private T type;

    public SymbolBase(Scope parent) {
        super(parent);
    }

    public SymbolBase(Scope parent, Token token) {
        super(parent);
        this.token = token;
    }

    public SymbolBase(Scope parent, T type) {
        super(parent);
        this.type = type;
    }

    public SymbolBase(Scope parent, T type, String scopeName) {
        super(parent, scopeName);
        this.type = type;
    }

    public SymbolBase(Scope parent, T type, Token token) {
        super(parent, token.getText());
        this.type = type;
        this.token = token;
    }

    @Override
    public T getType() {
        return type;
    }

    @Override
    public void setType(T type) {
        this.type = type;
    }

    @Override
    public Token getToken() {
        return token;
    }

    @Override
    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public String getSymbolName() {
        return token == null ? getScopeName() : token.getText();
    }

    @Override
    public String toString() {
        return this.getSymbolName() + " as " + type;
    }

}
