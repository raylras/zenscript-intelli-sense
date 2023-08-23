package raylras.zen.code.symbol;

public interface ParameterSymbol extends Symbol {

    boolean isOptional();

    boolean isVararg();

}
