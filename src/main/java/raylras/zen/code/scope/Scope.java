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
    public static Scope EMPTY = new Scope(null, null) {
        @Override
        public Symbol getSymbol(Class<Symbol> symbolClass, String name) {
            return null;
        }

        @Override
        public Symbol getSymbol(Predicate<Symbol> condition) {
            return null;
        }

        @Override
        public List<Symbol> getSymbols() {
            return Collections.emptyList();
        }

        @Override
        public void addSymbol(Symbol symbol) {
        }

        @Override
        public void removeSymbol(Symbol symbol) {
        }
    };

    public final List<Symbol> symbols = new ArrayList<>();
    public Scope parent;
    public ParseTree owner;

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

    public List<Symbol> getSymbols() {
        return ImmutableList.copyOf(symbols);
    }

    public void addSymbol(Symbol symbol) {
        symbols.add(symbol);
    }

    public void removeSymbol(Symbol symbol) {
        symbols.remove(symbol);
    }

}
