package raylras.zen.model.type;

public enum ShortType implements NumberType {

    INSTANCE;

    @Override
    public String getTypeName() {
        return "short";
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
