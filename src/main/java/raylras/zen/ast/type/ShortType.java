package raylras.zen.ast.type;

public final class ShortType implements Type {

    public static final ShortType INSTANCE = new ShortType();

    private ShortType() {}

    @Override
    public boolean equivalent(Type that) {
        return INSTANCE == that;
    }

    @Override
    public String toString() {
        return "short";
    }

}
