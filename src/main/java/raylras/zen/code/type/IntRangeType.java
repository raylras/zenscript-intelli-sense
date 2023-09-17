package raylras.zen.code.type;

import raylras.zen.code.SymbolProvider;
import raylras.zen.code.symbol.Operator;
import raylras.zen.code.symbol.Symbol;

import java.util.Collection;
import java.util.function.UnaryOperator;

public class IntRangeType extends Type implements SymbolProvider {

    public static final IntRangeType INSTANCE = new IntRangeType();

    @Override
    public String toString() {
        return "stanhebben.zenscript.value.IntRange";
    }

    @Override
    public Collection<Symbol> getSymbols() {
        return MembersBuilder.of()
                .variable("from", IntType.INSTANCE, Symbol.Modifier.VAL)
                .variable("to", IntType.INSTANCE, Symbol.Modifier.VAL)
                .operator(Operator.ITERATOR, new ListType(IntType.INSTANCE), UnaryOperator.identity())
                .build();
    }
}
