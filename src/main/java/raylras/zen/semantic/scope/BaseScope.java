package raylras.zen.semantic.scope;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.semantic.symbol.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BaseScope implements Scope {

    protected List<Symbol<?>> symbols;
    protected Scope parentScope;
    protected ParseTree node;
    private String name;

    public BaseScope(String name, Scope parentScope, ParseTree node) {
        Objects.requireNonNull(name);
        this.name = name;
        this.parentScope = parentScope;
        this.node = node;
    }

    @Override
    public void addSymbol(Symbol<?> symbol) {
        if (this.symbols == null) {
            symbols = new ArrayList<>();
        }
        symbol.setParentScope(this);
        symbols.add(symbol);
    }

    @Override
    public Symbol<?> findSymbol(String name) {
        Scope scopeToFind = this;
        while (scopeToFind != null) {
            if (scopeToFind.getSymbols() != null) {
                for (Symbol<?> symbol : scopeToFind.getSymbols()) {
                    if (symbol.getName().equals(name)) {
                        return symbol;
                    }
                }
            }
            scopeToFind = scopeToFind.getParentScope();
        }
        return null;
    }

    @Override
    public List<Symbol<?>> getSymbols() {
        return symbols;
    }

    @Override
    public Scope getParentScope() {
        return parentScope;
    }

    @Override
    public void setParentScope(Scope parentScope) {
        this.parentScope = parentScope;
    }

    @Override
    public ParseTree getNode() {
        return node;
    }

    @Override
    public void setNode(ParseTree node) {
        this.node = node;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    @Override
    public String toString() {
        return "(scope " + name + ")";
    }

}
