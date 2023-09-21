package raylras.zen.model.type;

import raylras.zen.model.SymbolProvider;
import raylras.zen.model.symbol.Operator;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolFactory;

import java.util.List;
import java.util.function.UnaryOperator;

public class IntRangeType extends Type implements SymbolProvider {

    public static final IntRangeType INSTANCE = new IntRangeType();

    @Override
    public String toString() {
        return "stanhebben.zenscript.value.IntRange";
    }

    @Override
    public List<Symbol> getSymbols() {
        return SymbolFactory.builtinSymbols()
                .variable("from", IntType.INSTANCE, Symbol.Modifier.VAL)
                .variable("to", IntType.INSTANCE, Symbol.Modifier.VAL)
                .operator(Operator.ITERATOR, new ListType(IntType.INSTANCE), UnaryOperator.identity())
                .build();
    }
}
