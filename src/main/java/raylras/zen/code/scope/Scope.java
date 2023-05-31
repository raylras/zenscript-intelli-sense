package raylras.zen.code.scope;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.symbol.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Scope {

    protected Scope parent;
    protected ParseTree owner;

    private final List<Symbol> symbols = new ArrayList<>();

    public Scope(Scope parent, ParseTree owner) {
        this.parent = parent;
        this.owner = owner;
    }

    public void addSymbol(Symbol symbol) {
        symbols.add(symbol);
    }

    public void removeSymbol(Symbol symbol) {
        symbols.remove(symbol);
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public Symbol lookupSymbol(String simpleName) {
        return lookupSymbol(Symbol.class, simpleName);
    }

    public <T extends Symbol> T lookupSymbol(Class<T> clazz, String simpleName) {
        Scope scope = this;
        while (scope != null) {
            for (Symbol symbol : scope.getSymbols()) {
                if (clazz.isInstance(symbol) && Objects.equals(symbol.getDeclaredName(), simpleName))
                    return clazz.cast(symbol);
            }
            scope = scope.parent;
        }
        return null;
    }

    public Scope getParent() {
        return parent;
    }

    public ParseTree getOwner() {
        return owner;
    }

}
