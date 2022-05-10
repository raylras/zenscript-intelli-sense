package raylras.zen.verify.type;

import java.util.Objects;

public class MapType extends AbstractType {

    private final Type keyType;
    private final Type valueType;

    public MapType(Type key, Type value) {
        this.keyType = Objects.requireNonNull(key);
        this.valueType = Objects.requireNonNull(value);
    }

    public Type getKeyType() {
        return keyType;
    }

    public Type getValueType() {
        return valueType;
    }

    @Override
    public String toString() {
        return valueType + "[" + keyType + "]";
    }

}
