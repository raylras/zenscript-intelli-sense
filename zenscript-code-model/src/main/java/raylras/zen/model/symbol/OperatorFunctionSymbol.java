package raylras.zen.model.symbol;


import raylras.zen.model.type.FunctionType;

public interface OperatorFunctionSymbol extends Symbol, Executable {

    Operator getOperator();

    @Override
    FunctionType getType();

}
