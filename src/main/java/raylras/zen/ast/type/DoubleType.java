package raylras.zen.ast.type;

public final class DoubleType implements Type {

    public static final DoubleType INSTANCE = new DoubleType();

    private DoubleType() {}

    @Override
    public boolean equivalent(Type that) {
        return INSTANCE == that;
    }

    @Override
    public String toString() {
        return "double";
    }
}
