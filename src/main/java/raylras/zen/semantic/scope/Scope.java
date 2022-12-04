package raylras.zen.semantic.scope;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.semantic.symbol.Symbol;

import java.util.ArrayList;
import java.util.List;

public class Scope {

    private String name;
    private List<Symbol> symbols;
    private Scope parentScope;
    private ParseTree node;

    public Scope(String name, Scope parentScope) {
        this.name = name;
        this.symbols = new ArrayList<>();
        this.parentScope = parentScope;
    }

    public void addSymbol(Symbol symbol) {
        symbol.setParentScope(this);
        symbols.add(symbol);
    }

    public Symbol findSymbol(String name) {
        Scope scopeToFind = this;
        while (scopeToFind != null) {
            for (Symbol symbol : scopeToFind.symbols) {
                if (symbol.getName().equals(name)) {
                    return symbol;
                }
            }
            scopeToFind = scopeToFind.parentScope;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scope getParentScope() {
        return parentScope;
    }

    public void setParentScope(Scope parentScope) {
        this.parentScope = parentScope;
    }

    public ParseTree getNode() {
        return node;
    }

    public void setNode(ParseTree node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return name;
    }

}
