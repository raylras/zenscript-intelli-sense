package raylras.zen.model.type;

public final class VoidType implements Type {

    public static final VoidType INSTANCE = new VoidType();

    @Override
    public String getTypeName() {
        return "void";
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
