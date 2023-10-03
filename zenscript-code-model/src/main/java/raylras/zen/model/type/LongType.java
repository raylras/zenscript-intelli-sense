package raylras.zen.model.type;

public final class LongType extends NumberType {

    public static final LongType INSTANCE = new LongType();

    @Override
    public String getTypeName() {
        return "long";
    }

}
