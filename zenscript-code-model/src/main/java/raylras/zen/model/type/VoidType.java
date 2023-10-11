package raylras.zen.model.type;

public enum VoidType implements Type {

    INSTANCE;

    @Override
    public String getTypeName() {
        return "void";
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
