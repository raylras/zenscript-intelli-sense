package raylras.zen.ast.type;

public final class IntType implements Type {

    public static final IntType INSTANCE = new IntType();

    private IntType() {}

    @Override
    public boolean equivalent(Type that) {
        return INSTANCE == that;
    }

    @Override
    public String toString() {
        return "int";
    }

}
