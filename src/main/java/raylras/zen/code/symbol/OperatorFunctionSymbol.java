package raylras.zen.code.symbol;

import raylras.zen.code.type.Type;

import java.util.List;

public interface OperatorFunctionSymbol extends Symbol {

    Operator getOperator();

    List<ParameterSymbol> getParameterList();

    Type getReturnType();

}
