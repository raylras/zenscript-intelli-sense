package raylras.zen.ast.type;

public record MapType(Type key, Type value) implements Type {

    @Override
    public boolean equivalent(Type type) {
        if (type instanceof MapType that) {
            // check key type
            if (!this.key.equivalent(that.key)) {
                return false;
            }
            // check value type
            return this.value.equivalent(that.value);
        }
        return false;
    }

    @Override
    public String toString() {
        return value + "[" + key + "]";
    }

}
