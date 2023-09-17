package raylras.zen.code.type;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.SymbolProvider;
import raylras.zen.code.symbol.Operator;
import raylras.zen.code.symbol.Symbol;

import java.util.Collection;
import java.util.function.UnaryOperator;

public class BoolType extends Type implements SymbolProvider {

    public static final BoolType INSTANCE = new BoolType();

    @Override
    public boolean isCastableTo(Type type, CompilationEnvironment env) {
        if (type == StringType.INSTANCE) {
            return true;
        }
        return super.isCastableTo(type, env);
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Collection<Symbol> getSymbols() {
        return MembersBuilder.of()
                .operator(Operator.AND, this, params -> params.parameter("val", this))
                .operator(Operator.OR, this, params -> params.parameter("val", this))
                .operator(Operator.XOR, this, params -> params.parameter("val", this))
                .operator(Operator.NOT, this, UnaryOperator.identity())
                .operator(Operator.CAT, StringType.INSTANCE, params -> params.parameter("str", StringType.INSTANCE))
                .operator(Operator.COMPARE, IntType.INSTANCE, params -> params.parameter("val", this))
                .build();
    }
}
