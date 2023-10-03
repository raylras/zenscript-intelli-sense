package raylras.zen.model.type;

public final class ShortType extends NumberType {

    public static final ShortType INSTANCE = new ShortType();

    @Override
    public String getTypeName() {
        return "short";
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
