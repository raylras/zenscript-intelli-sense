package raylras.zen.model.symbol;

import raylras.zen.model.SymbolProvider;

import java.util.List;

public interface PackageSymbol extends Symbol, SymbolProvider {

    String getQualifiedName();

    @Override
    List<Symbol> getSymbols();
}
