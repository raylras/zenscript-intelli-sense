package raylras.zen.code.symbol;

import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.Type;

public interface ExpandFunctionSymbol extends Symbol, Executable {

    Type getOwner();

    @Override
    FunctionType getType();
}
