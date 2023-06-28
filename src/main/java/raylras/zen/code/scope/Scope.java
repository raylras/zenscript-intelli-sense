package raylras.zen.code.scope;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.symbol.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Scope {

    private final Scope parent;
    private final ParseTree owner;
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

    public Symbol lookupSymbol(String identifier) {
        return lookupSymbol(Symbol.class, identifier);
    }

    public <T extends Symbol> T lookupSymbol(Class<T> clazz, String identifier) {
        Scope scope = this;
        while (scope != null) {
            for (Symbol symbol : scope.getSymbols()) {
                if (clazz.isInstance(symbol)
                        && Objects.equals(symbol.getDeclaredName(), identifier)) {
                    return clazz.cast(symbol);
                }
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

    public List<Symbol> getSymbols() {
        return symbols;
    }

}
