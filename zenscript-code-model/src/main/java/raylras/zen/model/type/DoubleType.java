package raylras.zen.model.type;

public final class DoubleType extends NumberType {

    public static final DoubleType INSTANCE = new DoubleType();

    @Override
    public String getTypeName() {
        return "double";
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
