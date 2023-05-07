package raylras.zen.code.scope;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.symbol.Symbol;

import java.util.ArrayList;
import java.util.List;

public class Scope {

    public Scope parent;
    public ParseTree owner;

    public final List<Symbol> symbols = new ArrayList<>();

    public Scope() {
    }

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

}
