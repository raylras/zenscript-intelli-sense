package raylras.zen.model.symbol;

import java.util.Collection;

public interface PackageSymbol extends Symbol, SymbolProvider {

    String getQualifiedName();

    Collection<PackageSymbol> getSubpackages();

    Collection<Symbol> getMembers();

}
