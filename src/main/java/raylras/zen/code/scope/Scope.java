package raylras.zen.code.scope;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.symbol.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Scope {

    private Scope parent;
    private ParseTree owner;

    private final List<Symbol> symbols = new ArrayList<>();

    public Scope(Scope parent, ParseTree owner) {
        this.parent = parent;
        this.owner = owner;
    }

    public Symbol getSymbol(String name) {
        for (Symbol symbol : symbols) {
            if (Objects.equals(name, symbol.getName()))
                return symbol;
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

    public Scope getParent() {
        return parent;
    }

    public void setParent(Scope parent) {
        this.parent = parent;
    }

    public ParseTree getOwner() {
        return owner;
    }

    public void setOwner(ParseTree owner) {
        this.owner = owner;
    }

}
