package raylras.zen.code.symbol;

import java.util.Collection;

public interface ImportSymbol extends Symbol {

    String getQualifiedName();

    Collection<Symbol> getTargets();

}
