package raylras.zen.code.symbol;

import raylras.zen.code.SymbolProvider;

public interface PackageSymbol extends Symbol, SymbolProvider {

    String getQualifiedName();

    @Override
    SymbolGroup getSymbols();
}
