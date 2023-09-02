package raylras.zen.code.symbol;

import raylras.zen.code.SymbolProvider;

import java.util.List;

public interface PackageSymbol extends Symbol, SymbolProvider {

    String getQualifiedName();

    @Override
    List<Symbol> getSymbols();
}
