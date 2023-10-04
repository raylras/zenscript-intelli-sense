package raylras.zen.model.type;

public enum FloatType implements NumberType {

    INSTANCE;

    @Override
    public String getTypeName() {
        return "float";
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
