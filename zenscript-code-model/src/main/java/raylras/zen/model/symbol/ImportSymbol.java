package raylras.zen.model.symbol;

public interface ImportSymbol extends Symbol, SymbolProvider<Symbol> {

    String getQualifiedName();

}
