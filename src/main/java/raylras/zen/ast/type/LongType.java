package raylras.zen.ast.type;

public final class LongType implements Type {

    public static final LongType INSTANCE = new LongType();

    private LongType() {}

    @Override
    public boolean equivalent(Type that) {
        return INSTANCE == that;
    }

    @Override
    public String toString() {
        return "long";
    }
    
}
