package raylras.zen.code.symbol;

import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.Type;

import java.util.List;

public interface FunctionSymbol extends Symbol {

    List<ParameterSymbol> getParameterList();

    Type getReturnType();

    @Override
    FunctionType getType();

}
