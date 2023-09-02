package raylras.zen.code.scope;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.SymbolProvider;
import raylras.zen.code.symbol.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Scope implements SymbolProvider {

    private final Scope parent;
    private final List<Symbol> symbols = new ArrayList<>();
    private final ParseTree cst;

    public Scope(Scope parent, ParseTree cst) {
        this.parent = parent;
        this.cst = cst;
    }

    public void addSymbol(Symbol symbol) {
        symbols.add(symbol);
    }

    public void removeSymbol(Symbol symbol) {
        symbols.remove(symbol);
    }

    public Symbol lookupSymbol(String simpleName) {
        return lookupSymbol(Symbol.class, simpleName);
    }
    public List<Symbol> lookupSymbols(String simpleName) {
        Scope scope = this;
        List<Symbol> result = new ArrayList<>();
        while (scope != null) {

            for (Symbol symbol : scope.getSymbols()) {
                if (Objects.equals(symbol.getName(), simpleName)) {
                    result.add(symbol);
                }
            }
            // assume overload only happen on same scope
            if(!result.isEmpty()) {
                return result;
            }
            scope = scope.parent;
        }
        return result;
    }

    public <T extends Symbol> T lookupSymbol(Class<T> clazz, String simpleName) {
        Scope scope = this;
        while (scope != null) {
            for (Symbol symbol : scope.getSymbols()) {
                if (clazz.isInstance(symbol)
                        && Objects.equals(symbol.getName(), simpleName)) {
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

    @Override
    public List<Symbol> getSymbols() {
        return symbols;
    }

    public ParseTree getCst() {
        return cst;
    }

}
