package raylras.zen.code.type;

import raylras.zen.code.common.MemberProvider;
import raylras.zen.code.symbol.Operator;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.SymbolFactory;

import java.util.List;
import java.util.function.UnaryOperator;

public class IntRangeType extends Type implements MemberProvider {

    public static final IntRangeType INSTANCE = new IntRangeType();

    @Override
    public String toString() {
        return "stanhebben.zenscript.value.IntRange";
    }

    @Override
    public List<Symbol> getMembers() {
        return SymbolFactory.members()
                .variable("from", IntType.INSTANCE, Symbol.Modifier.VAL)
                .variable("to", IntType.INSTANCE, Symbol.Modifier.VAL)
                .operator(Operator.ITERATOR, new ListType(IntType.INSTANCE), UnaryOperator.identity())
                .build();
    }
}
