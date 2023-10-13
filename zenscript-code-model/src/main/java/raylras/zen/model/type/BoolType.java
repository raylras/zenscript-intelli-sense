package raylras.zen.model.type;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.symbol.Operator;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolFactory;
import raylras.zen.model.symbol.SymbolProvider;

import java.util.List;

public enum BoolType implements Type, SymbolProvider {

    INSTANCE;

    @Override
    public String getTypeName() {
        return "bool";
    }

    @Override
    public boolean isCastableTo(Type type, CompilationEnvironment env) {
        if (type instanceof StringType) {
            return true;
        }
        return Type.super.isCastableTo(type, env);
    }

    @Override
    public List<Symbol> getSymbols() {
        return SymbolFactory.builtinSymbols()
                .operator(Operator.NOT, this)
                .operator(Operator.AND, this, params -> params.parameter("value", this))
                .operator(Operator.OR, this, params -> params.parameter("value", this))
                .operator(Operator.XOR, this, params -> params.parameter("value", this))
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
