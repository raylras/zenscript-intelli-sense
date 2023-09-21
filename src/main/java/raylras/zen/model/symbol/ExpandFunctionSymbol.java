package raylras.zen.model.symbol;

import raylras.zen.model.type.Type;

public interface ExpandFunctionSymbol extends Symbol, Executable {

    Type getExpandingType();

}
