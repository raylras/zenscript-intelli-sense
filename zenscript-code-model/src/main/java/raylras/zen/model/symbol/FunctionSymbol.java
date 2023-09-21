package raylras.zen.model.symbol;

import raylras.zen.model.type.FunctionType;

public interface FunctionSymbol extends Symbol, Executable {

    @Override
    FunctionType getType();

}
