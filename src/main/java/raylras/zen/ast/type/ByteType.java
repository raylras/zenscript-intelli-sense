package raylras.zen.ast.type;

public final class ByteType implements Type {

    public static final ByteType INSTANCE = new ByteType();

    private ByteType() {}

    @Override
    public boolean equivalent(Type that) {
        return INSTANCE == that;
    }

    @Override
    public String toString() {
        return "byte";
    }

}
