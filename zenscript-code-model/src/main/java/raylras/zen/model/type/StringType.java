package raylras.zen.model.type;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.symbol.SymbolProvider;
import raylras.zen.model.symbol.Operator;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolFactory;

import java.util.List;

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
    public List<Symbol> getSymbols() {
        return SymbolFactory.builtinSymbols()
                .operator(Operator.ADD, this, params -> params.parameter("str", this))
                .operator(Operator.CAT, this, params -> params.parameter("str", this))
                .operator(Operator.INDEX_GET, this, params -> params.parameter("index", IntType.INSTANCE))
                .operator(Operator.COMPARE, IntType.INSTANCE, params -> params.parameter("str", this))
                .operator(Operator.HAS, BoolType.INSTANCE, params -> params.parameter("str", this))
                .build();
    }

}
