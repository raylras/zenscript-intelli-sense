package raylras.zen.ast.type;

import java.util.Objects;

public class TypeMap extends Type {

    private final Type keyType;
    private final Type valueType;
    private final String typeName;

    public TypeMap(Type key, Type value) {
        this.keyType = Objects.requireNonNull(key);
        this.valueType = Objects.requireNonNull(value);
        this.typeName = valueType.getTypeName() + "[" + keyType.getTypeName() + "]";
    }

    public Type getKeyType() {
        return keyType;
    }

    public Type getValueType() {
        return valueType;
    }

    @Override
    public String toString() {
        return typeName;
    }

}
