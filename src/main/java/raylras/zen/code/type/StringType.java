package raylras.zen.code.type;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.SymbolProvider;
import raylras.zen.code.symbol.Operator;
import raylras.zen.code.symbol.Symbol;

import java.util.Collection;

public class StringType extends Type implements SymbolProvider {

    public static final StringType INSTANCE = new StringType();

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public boolean isCastableTo(Type type, CompilationEnvironment env) {
        if (type instanceof NumberType || BoolType.INSTANCE.equals(type)) {
            return true;
        }
        return super.isCastableTo(type, env);
    }

    @Override
    public Collection<Symbol> getSymbols() {
        return MembersBuilder.of()
                .operator(Operator.ADD, this, params -> params.parameter("str", this))
                .operator(Operator.CAT, this, params -> params.parameter("str", this))
                .operator(Operator.INDEX_GET, this, params -> params.parameter("index", IntType.INSTANCE))
                .operator(Operator.COMPARE, IntType.INSTANCE, params -> params.parameter("str", this))
                .operator(Operator.HAS, BoolType.INSTANCE, params -> params.parameter("str", this))
                .build();
    }

}
