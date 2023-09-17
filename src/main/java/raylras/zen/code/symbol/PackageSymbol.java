package raylras.zen.code.symbol;

import raylras.zen.code.SymbolProvider;

import java.util.Collection;

public interface PackageSymbol extends Symbol, SymbolProvider {

    String getQualifiedName();

    @Override
    Collection<Symbol> getSymbols();
}
