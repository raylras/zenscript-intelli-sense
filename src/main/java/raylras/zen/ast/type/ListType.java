package raylras.zen.ast.type;

public final class ListType implements Type {

    private final Type base;

    public ListType(Type base) {
        this.base = base;
    }

    public Type getBase() {
        return base;
    }

    @Override
    public String toString() {
        return "[" + base + "]";
    }

}
