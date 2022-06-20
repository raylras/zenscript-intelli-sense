package raylras.zen.ast.type;

public final class VoidType implements Type {

    public static final VoidType INSTANCE = new VoidType();

    private VoidType() {}

    @Override
    public boolean equivalent(Type that) {
        return INSTANCE == that;
    }

    @Override
    public String toString() {
        return "void";
    }
    
}
