package raylras.zen.ast.type;

import java.util.Objects;

public final class MapType implements Type {

    private final Type key;
    private final Type value;

    public MapType(Type key, Type value) {
        this.key = Objects.requireNonNull(key);
        this.value = Objects.requireNonNull(value);
    }

    public Type getKey() {
        return key;
    }

    public Type getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + "[" + key + "]";
    }

}
