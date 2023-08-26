package raylras.zen.code.symbol;

import raylras.zen.code.type.FunctionType;

public interface FunctionSymbol extends Symbol, Executable {

    @Override
    FunctionType getType();

}
