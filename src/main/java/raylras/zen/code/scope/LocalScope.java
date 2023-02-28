package raylras.zen.code.scope;

import raylras.zen.code.symbol.Symbol;

import java.util.*;

public class LocalScope extends Scope {

    public LocalScope parent;
    public Map<String, List<Symbol>> symbolTable;

    public LocalScope(Symbol owner, LocalScope parent) {
        super(owner);
        this.parent = parent;
        this.symbolTable = new HashMap<>();
    }

    @Override
    public void add(Symbol symbol) {
        symbolTable.computeIfAbsent(symbol.name, name -> new ArrayList<>()).add(symbol);
    }

    @Override
    public void remove(Symbol symbol) {
        symbolTable.computeIfAbsent(symbol.name, name -> new ArrayList<>()).remove(symbol);
    }

    @Override
    public Iterable<Symbol> getSymbols(boolean recursive) {
        // TODO
        return Collections.emptyList();
    }

    @Override
    public Iterable<Symbol> getSymbolsByName(String name, boolean recursive) {
        // TODO
        return Collections.emptyList();
    }

}
