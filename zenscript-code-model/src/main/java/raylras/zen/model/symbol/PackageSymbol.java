package raylras.zen.model.symbol;

import java.util.List;

public interface PackageSymbol extends Symbol, SymbolProvider<Symbol> {

    String getQualifiedName();

    @Override
    List<Symbol> getSymbols();
}
