package raylras.zen.model.symbol;

public interface PackageSymbol extends Symbol, SymbolProvider<Symbol> {

    String getQualifiedName();

}
