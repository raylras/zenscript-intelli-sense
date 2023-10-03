package raylras.zen.model.type;

import raylras.zen.model.symbol.Operator;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolFactory;
import raylras.zen.model.symbol.SymbolProvider;

import java.util.List;

public final class StringType implements Type, SymbolProvider {

    public static final StringType INSTANCE = new StringType();

    @Override
    public String getTypeName() {
        return "string";
    }

    @Override
    public List<Symbol> getSymbols() {
        return SymbolFactory.builtinSymbols()
                .operator(Operator.ADD, this, params -> params.parameter("str", this))
                .operator(Operator.CONCAT, this, params -> params.parameter("str", this))
                .operator(Operator.HAS, BoolType.INSTANCE, params -> params.parameter("str", this))
                .operator(Operator.INDEX_GET, this, params -> params.parameter("index", IntType.INSTANCE))
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
