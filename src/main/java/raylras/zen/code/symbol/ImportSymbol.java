package raylras.zen.code.symbol;

import java.util.List;

public interface ImportSymbol extends Symbol {

    String getQualifiedName();

    List<Symbol> getTargets();

}
