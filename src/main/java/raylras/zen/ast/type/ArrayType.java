package raylras.zen.ast.type;

public record ArrayType(Type base) implements Type {

    @Override
    public boolean equivalent(Type type) {
        if (type instanceof ArrayType that) {
            return this.base.equivalent(that.base);
        }
        return false;
    }

    @Override
    public String toString() {
        return base + "[]";
    }

}
