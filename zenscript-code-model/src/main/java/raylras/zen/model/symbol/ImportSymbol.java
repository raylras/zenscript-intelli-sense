package raylras.zen.model.symbol;

import java.util.Collection;

public interface ImportSymbol extends Symbol, SymbolProvider {

    String getQualifiedName();

    Collection<Symbol> getTargets();

}
