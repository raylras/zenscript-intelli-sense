package raylras.zen.code.scope;

import raylras.zen.code.symbol.Symbol;

public abstract class Scope {

    public Symbol owner;

    protected Scope(Symbol owner) {
        this.owner = owner;
    }

    public abstract void add(Symbol symbol);

    public abstract void remove(Symbol symbol);

    public abstract Iterable<Symbol> getSymbols(boolean recursive);

    public abstract Iterable<Symbol> getSymbolsByName(String name, boolean recursive);

}
