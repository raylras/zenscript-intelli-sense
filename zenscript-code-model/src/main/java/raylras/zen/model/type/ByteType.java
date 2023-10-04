package raylras.zen.model.type;

public enum ByteType implements NumberType {

    INSTANCE;

    @Override
    public String getTypeName() {
        return "byte";
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
