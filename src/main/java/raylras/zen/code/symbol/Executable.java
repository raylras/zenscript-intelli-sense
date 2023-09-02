package raylras.zen.code.symbol;

import raylras.zen.code.type.Type;

import java.util.List;

public interface Executable {

    List<ParameterSymbol> getParameterList();

    Type getReturnType();

}
