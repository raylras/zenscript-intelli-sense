package raylras.zen.code.type;

import raylras.zen.code.symbol.Operator;
import raylras.zen.code.symbol.SymbolFactory;
import raylras.zen.code.symbol.SymbolGroup;

public class IntType extends NumberType {

    public static final IntType INSTANCE = new IntType();

    @Override
    public SymbolGroup getSymbols() {
        return SymbolFactory.builtinSymbols()
                .add(super.getSymbols())
                .operator(Operator.RANGE, IntRangeType.INSTANCE, params -> params.parameter("val", this))
                .build();
    }

    @Override
    public String toString() {
        return "int";
    }

}
