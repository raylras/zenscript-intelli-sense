package raylras.zen.model.symbol;

import java.util.List;

public interface ImportSymbol extends Symbol {

    String getQualifiedName();

    List<Symbol> getTargets();

}
