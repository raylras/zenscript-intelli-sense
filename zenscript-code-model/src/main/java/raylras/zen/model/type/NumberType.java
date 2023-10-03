package raylras.zen.model.type;

import raylras.zen.model.symbol.Operator;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolFactory;
import raylras.zen.model.symbol.SymbolProvider;

import java.util.List;

public abstract sealed class NumberType implements Type, SymbolProvider
        permits ByteType, DoubleType, FloatType, IntType, LongType, ShortType {

    @Override
    public List<Symbol> getSymbols() {
        return SymbolFactory.builtinSymbols()
                .operator(Operator.ADD, this, params -> params.parameter("value", this))
                .operator(Operator.SUB, this, params -> params.parameter("value", this))
                .operator(Operator.MUL, this, params -> params.parameter("value", this))
                .operator(Operator.DIV, this, params -> params.parameter("value", this))
                .operator(Operator.MOD, this, params -> params.parameter("value", this))
                .operator(Operator.NEG, this, params -> params.parameter("value", this))
                .operator(Operator.CONCAT, StringType.INSTANCE, params -> params.parameter("str", StringType.INSTANCE))
                .operator(Operator.EQUALS, BoolType.INSTANCE, params -> params.parameter("value", this))
                .operator(Operator.NOT_EQUALS, BoolType.INSTANCE, params -> params.parameter("value", this))
                .operator(Operator.LESS, BoolType.INSTANCE, params -> params.parameter("value", this))
                .operator(Operator.LESS_EQUALS, BoolType.INSTANCE, params -> params.parameter("value", this))
                .operator(Operator.GREATER, BoolType.INSTANCE, params -> params.parameter("value", this))
                .operator(Operator.GREATER_EQUALS, BoolType.INSTANCE, params -> params.parameter("value", this))
                .build();
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
