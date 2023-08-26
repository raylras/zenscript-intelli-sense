package raylras.zen.code.type;

import raylras.zen.code.symbol.Operator;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.SymbolFactory;

import java.util.List;
import java.util.function.UnaryOperator;

public abstract class NumberType extends Type {

    @Override
    public SubtypeResult isSubtypeOf(Type type) {
        if (type instanceof NumberType) {
            return SubtypeResult.INHERIT;
        }
        return super.isSubtypeOf(type);
    }

    @Override
    public List<Symbol> getMembers() {
        return SymbolFactory.members()
                .operator(Operator.ADD, this, params -> params.parameter("val", this))
                .operator(Operator.SUB, this, params -> params.parameter("val", this))
                .operator(Operator.MUL, this, params -> params.parameter("val", this))
                .operator(Operator.DIV, this, params -> params.parameter("val", this))
                .operator(Operator.MOD, this, params -> params.parameter("val", this))
                .operator(Operator.CAT, StringType.INSTANCE, params -> params.parameter("str", StringType.INSTANCE))
                .operator(Operator.NEG, this, UnaryOperator.identity())
                .build();
    }
}
