package raylras.zen.model.type;

import raylras.zen.model.symbol.Operator;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolFactory;

import java.util.List;

public final class IntType extends NumberType {

    public static final IntType INSTANCE = new IntType();

    @Override
    public String getTypeName() {
        return "int";
    }

    @Override
    public List<Symbol> getSymbols() {
        return SymbolFactory.builtinSymbols()
                .add(super.getSymbols())
                .operator(Operator.RANGE, IntRangeType.INSTANCE, params -> params.parameter("value", this))
                .build();
    }

}
