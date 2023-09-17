package raylras.zen.code.type;

import raylras.zen.code.symbol.Operator;
import raylras.zen.code.symbol.Symbol;

import java.util.Collection;

public class IntType extends NumberType {

    public static final IntType INSTANCE = new IntType();

    @Override
    public Collection<Symbol> getSymbols() {
        return MembersBuilder.of()
                .addAll(super.getSymbols())
                .operator(Operator.RANGE, IntRangeType.INSTANCE, params -> params.parameter("val", this))
                .build();
    }

    @Override
    public String toString() {
        return "int";
    }

}
