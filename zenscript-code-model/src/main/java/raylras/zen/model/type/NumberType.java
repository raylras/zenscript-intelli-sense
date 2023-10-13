package raylras.zen.model.type;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.symbol.Operator;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolFactory;
import raylras.zen.model.symbol.SymbolProvider;

import java.util.List;

public sealed interface NumberType extends Type, SymbolProvider
        permits ByteType, DoubleType, FloatType, IntType, LongType, ShortType {

    @Override
    default boolean isCastableTo(Type type, CompilationEnvironment env) {
        if (type instanceof NumberType || type instanceof StringType) {
            return true;
        }
        return Type.super.isCastableTo(type, env);
    }

    @Override
    default List<Symbol> getSymbols() {
        return SymbolFactory.builtinSymbols()
                .operator(Operator.NEG, this)
                .operator(Operator.ADD, this, params -> params.parameter("value", this))
                .operator(Operator.SUB, this, params -> params.parameter("value", this))
                .operator(Operator.MUL, this, params -> params.parameter("value", this))
                .operator(Operator.DIV, this, params -> params.parameter("value", this))
                .operator(Operator.MOD, this, params -> params.parameter("value", this))
                .operator(Operator.CONCAT, StringType.INSTANCE, params -> params.parameter("str", StringType.INSTANCE))
                .operator(Operator.EQUALS, BoolType.INSTANCE, params -> params.parameter("value", this))
                .operator(Operator.NOT_EQUALS, BoolType.INSTANCE, params -> params.parameter("value", this))
                .operator(Operator.LESS, BoolType.INSTANCE, params -> params.parameter("value", this))
                .operator(Operator.LESS_EQUALS, BoolType.INSTANCE, params -> params.parameter("value", this))
                .operator(Operator.GREATER, BoolType.INSTANCE, params -> params.parameter("value", this))
                .operator(Operator.GREATER_EQUALS, BoolType.INSTANCE, params -> params.parameter("value", this))
                .build();
    }

}
