package raylras.zen.ast.type;

public record ListType(Type base) implements Type {

    @Override
    public boolean equivalent(Type type) {
        if (type instanceof ListType that) {
            return this.base.equivalent(that.base);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[" + base + "]";
    }

}
