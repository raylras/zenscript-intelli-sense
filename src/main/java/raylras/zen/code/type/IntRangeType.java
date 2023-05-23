package raylras.zen.code.type;

import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.NativeClassSymbol;

public class IntRangeType extends ClassType {
    public static final IntRangeType INSTANCE = new IntRangeType();

    public IntRangeType() {
        super("stanhebben.zenscript.value.IntRange", null);
    }

    @Override
    public ClassSymbol getSymbol() {
        return NativeClassSymbol.INT_RANGE;
    }

    @Override
    public Kind getKind() {
        return Kind.INT_RANGE;
    }
}
