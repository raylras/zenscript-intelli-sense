package raylras.zen.model.type;

public enum LongType implements NumberType {

    INSTANCE;

    @Override
    public String getTypeName() {
        return "long";
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
