package raylras.zen.code.scope;

import com.google.common.collect.ImmutableList;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.symbol.Symbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Scope {

    public static final Scope EMPTY = new Scope(null, null);
    protected final Scope parent;
    protected final ParseTree owner;

    private final List<Symbol> symbols = new ArrayList<>();

    public Scope(Scope parent, ParseTree owner) {
        this.parent = parent;
        this.owner = owner;
    }

    public Symbol getSymbol(Class<Symbol> symbolClass, String name) {
        return getSymbol(symbol ->
            symbolClass.isInstance(symbol) &&
                Objects.equals(symbol.getName(), name)
        );
    }

    public Symbol getSymbol(Predicate<Symbol> condition) {
        for (Symbol symbol : symbols) {
            if (condition.test(symbol)) {
                return symbol;
            }
        }
        return null;
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
                if (clazz.isInstance(symbol) && Objects.equals(symbol.getName(), simpleName))
                    return clazz.cast(symbol);
            }
            scope = scope.parent;
        }
        return null;
    }


    public <T extends Symbol> List<T> lookupSymbols(Class<T> type, Predicate<T> condition) {
        Scope scope = this;
        List<T> result = new ArrayList<>();
        while (scope != null) {
            for (Symbol symbol : scope.getSymbols()) {
                if (type.isInstance(symbol) && condition.test(type.cast(symbol))) {
                    result.add(type.cast(symbol));
                }
            }
            scope = scope.parent;
        }

        return result;
    }


    public Scope getParent() {
        return parent;
    }

    public ParseTree getOwner() {
        return owner;
    }

}
