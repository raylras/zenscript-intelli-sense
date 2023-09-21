package raylras.zen.model.type;

import raylras.zen.model.symbol.Operator;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolFactory;

import java.util.List;

public class IntType extends NumberType {

    public static final IntType INSTANCE = new IntType();

    @Override
    public List<Symbol> getSymbols() {
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
