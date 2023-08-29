package raylras.zen.code.symbol;


import raylras.zen.code.type.FunctionType;

public interface OperatorFunctionSymbol extends Symbol, Executable {

    Operator getOperator();

    @Override
    FunctionType getType();

}
