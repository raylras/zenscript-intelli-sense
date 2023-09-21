package raylras.zen.model.type;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.SymbolProvider;
import raylras.zen.model.symbol.Operator;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolFactory;

import java.util.List;
import java.util.function.UnaryOperator;

public abstract class NumberType extends Type implements SymbolProvider {

    @Override
    public boolean isInheritedFrom(Type type) {
        if (type instanceof NumberType) {
            return true;
        }
        return super.isInheritedFrom(type);
    }

    @Override
    public boolean isCastableTo(Type type, CompilationEnvironment env) {
        if (type == StringType.INSTANCE) {
            return true;
        }
        return super.isCastableTo(type, env);
    }

    @Override
    public List<Symbol> getSymbols() {
        return SymbolFactory.builtinSymbols()
                .operator(Operator.ADD, this, params -> params.parameter("val", this))
                .operator(Operator.SUB, this, params -> params.parameter("val", this))
                .operator(Operator.MUL, this, params -> params.parameter("val", this))
                .operator(Operator.DIV, this, params -> params.parameter("val", this))
                .operator(Operator.MOD, this, params -> params.parameter("val", this))
                .operator(Operator.CAT, StringType.INSTANCE, params -> params.parameter("str", StringType.INSTANCE))
                .operator(Operator.COMPARE, IntType.INSTANCE, params -> params.parameter("val", this))
                .operator(Operator.NEG, this, UnaryOperator.identity())
                .build();
    }
}
