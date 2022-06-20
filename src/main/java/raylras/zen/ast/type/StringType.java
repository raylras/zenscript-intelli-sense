package raylras.zen.ast.type;

public final class StringType implements Type {

    public static final StringType INSTANCE = new StringType();

    private StringType() {}

    @Override
    public boolean equivalent(Type that) {
        return INSTANCE == that;
    }

    @Override
    public String toString() {
        return "string";
    }

}
