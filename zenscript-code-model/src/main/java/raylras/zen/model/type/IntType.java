package raylras.zen.model.type;

import raylras.zen.model.symbol.Operator;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolFactory;

import java.util.List;

public enum IntType implements NumberType {

    INSTANCE;

    @Override
    public String getTypeName() {
        return "int";
    }

    @Override
    public List<Symbol> getSymbols() {
        return SymbolFactory.builtinSymbols()
                .add(NumberType.super.getSymbols())
                .operator(Operator.RANGE, IntRangeType.INSTANCE, params -> params.parameter("value", this))
                .build();
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
