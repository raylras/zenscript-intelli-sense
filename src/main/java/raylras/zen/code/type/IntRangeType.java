package raylras.zen.code.type;

import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.SymbolFactory;

import java.util.List;

public class IntRangeType extends Type {

    public static final IntRangeType INSTANCE = new IntRangeType();

    @Override
    public String toString() {
        return "stanhebben.zenscript.value.IntRange";
    }

    @Override
    public List<Symbol> getMembers() {
        return SymbolFactory.members()
                .variable("from", IntType.INSTANCE, Symbol.Modifier.VAL)
                .variable("to", INSTANCE, Symbol.Modifier.VAL)
                .build();
    }
}
