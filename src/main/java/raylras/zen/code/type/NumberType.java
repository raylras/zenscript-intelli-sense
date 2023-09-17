package raylras.zen.code.type;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.SymbolProvider;
import raylras.zen.code.symbol.Operator;
import raylras.zen.code.symbol.Symbol;

import java.util.Collection;
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
    public Collection<Symbol> getSymbols() {
        return MembersBuilder.of()
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
