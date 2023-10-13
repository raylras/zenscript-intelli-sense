package raylras.zen.model.type;

import raylras.zen.model.symbol.Operator;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolFactory;
import raylras.zen.model.symbol.SymbolProvider;

import java.util.List;
import java.util.function.UnaryOperator;

public enum IntRangeType implements Type, SymbolProvider {

    INSTANCE;

    @Override
    public String getTypeName() {
        return "stanhebben.zenscript.value.IntRange";
    }

    @Override
    public List<Symbol> getSymbols() {
        return SymbolFactory.builtinSymbols()
                .variable("from", IntType.INSTANCE, Symbol.Modifier.IMPLICIT_VAL)
                .variable("to", IntType.INSTANCE, Symbol.Modifier.IMPLICIT_VAL)
                .operator(Operator.FOR_IN, new ListType(IntType.INSTANCE), UnaryOperator.identity())
                .build();
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
