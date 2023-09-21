package raylras.zen.model.symbol;

import raylras.zen.model.type.Type;

import java.util.List;

public interface Executable {

    List<ParameterSymbol> getParameterList();

    Type getReturnType();

}
