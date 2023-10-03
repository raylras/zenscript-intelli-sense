package raylras.zen.model.type;

public final class ByteType extends NumberType {

    public static final ByteType INSTANCE = new ByteType();

    @Override
    public String getTypeName() {
        return "byte";
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
