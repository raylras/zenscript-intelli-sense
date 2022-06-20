package raylras.zen.ast.type;

public final class FloatType implements Type {

    public static final FloatType INSTANCE = new FloatType();

    private FloatType() {}

    @Override
    public boolean equivalent(Type that) {
        return INSTANCE == that;
    }

    @Override
    public String toString() {
        return "float";
    }

}
