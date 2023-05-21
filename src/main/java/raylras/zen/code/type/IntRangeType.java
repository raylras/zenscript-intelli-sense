package raylras.zen.code.type;

public class IntRangeType extends NamedType {
    public static final IntRangeType INSTANCE = new IntRangeType();

    private IntRangeType() {
        super("IntRange");
    }

    @Override
    public Kind getKind() {
        return Kind.INT_RANGE;
    }
}
