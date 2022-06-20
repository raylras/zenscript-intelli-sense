package raylras.zen.ast.type;

public final class BoolType implements Type {

    public static final BoolType INSTANCE = new BoolType();

    private BoolType() {}

    @Override
    public boolean equivalent(Type that) {
        return INSTANCE == that;
    }

    @Override
    public String toString() {
        return "bool";
    }

}
