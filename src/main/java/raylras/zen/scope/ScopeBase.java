package raylras.zen.scope;

import raylras.zen.exception.AlreadyDefinedException;
import raylras.zen.symbol.Symbol;
import raylras.zen.type.Type;

import java.util.HashMap;
import java.util.Map;

public abstract class ScopeBase implements Scope {

    private Scope parent;
    private String scopeName;
    private Map<String, Symbol<? extends Type>> symbols;

    public ScopeBase(Scope parent) {
        this.parent = parent;
    }

    public ScopeBase(Scope parent, String scopeName) {
        this.parent = parent;
        this.scopeName = scopeName;
    }

    @Override
    public Scope getParent() {
        return parent;
    }

    @Override
    public void setParent(Scope parent) {
        this.parent = parent;
    }

    @Override
    public String getScopeName() {
        return scopeName;
    }

    @Override
    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    @Override
    public Map<String, Symbol<? extends Type>> getSymbols() {
        return symbols;
    }

    @Override
    public <T extends Type> void define(Symbol<T> symbol) {
        if (symbol == null) {
            return;
        }

        if (symbols == null) {
            symbols = new HashMap<>();
        }

        Symbol<? extends Type> old;
        if ((old = symbols.get(symbol.getSymbolName())) != null) {
            String message = String.format("Symbol %s already defined in Scope %s", symbol, this);
            throw new AlreadyDefinedException(message, this, old, symbol);
        }

        symbols.put(symbol.getSymbolName(), symbol);
    }

    @Override
    public <T extends Type> void define(String signature, Symbol<T> symbol) {
        if (signature == null) {
            return;
        }

        if (symbols == null) {
            symbols = new HashMap<>();
        }

        Symbol<? extends Type> old;
        if ((old = symbols.get(signature)) != null) {
            String message = String.format("Symbol %s already defined in Scope %s", symbol, this);
            throw new AlreadyDefinedException(message, this, old, symbol);
        }

        if (symbol != null) {
            symbols.put(signature, symbol);
        }
    }

    @Override
    public Symbol<? extends Type> resolve(String name) {
        if (name == null) {
            return null;
        }

        Symbol<? extends Type> result = symbols.get(name);
        if (result != null) {
            return result;
        }

        if (parent != null) {
            return parent.resolve(name);
        }

        return null;
    }

    @Override
    public String toString() {
        return scopeName;
    }

}
