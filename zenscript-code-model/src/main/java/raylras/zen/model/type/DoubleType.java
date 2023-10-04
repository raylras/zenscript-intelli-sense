package raylras.zen.model.type;

public enum DoubleType implements NumberType {

    INSTANCE;

    @Override
    public String getTypeName() {
        return "double";
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
