package raylras.zen.model.type;

public final class FloatType extends NumberType {

    public static final FloatType INSTANCE = new FloatType();

    @Override
    public String getTypeName() {
        return "float";
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
