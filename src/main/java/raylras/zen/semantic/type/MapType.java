package raylras.zen.semantic.type;

import java.util.Objects;

public class MapType implements Type {

    private Type keyType;
    private Type valueType;

    public MapType(Type keyType, Type valueType) {
        Objects.requireNonNull(keyType);
        Objects.requireNonNull(valueType);
        this.keyType = keyType;
        this.valueType = valueType;
    }

    public Type getKeyType() {
        return keyType;
    }

    public void setKeyType(Type keyType) {
        Objects.requireNonNull(keyType);
        this.keyType = keyType;
    }

    public Type getValueType() {
        return valueType;
    }

    public void setValueType(Type valueType) {
        Objects.requireNonNull(valueType);
        this.valueType = valueType;
    }

    @Override
    public String typeName() {
        return valueType.typeName() + "[" + keyType.typeName() + "]";
    }

    @Override
    public boolean isType(Type type) {
        if (type instanceof MapType) {
            MapType that = (MapType) type;
            return this.keyType.isType(that.keyType) && this.valueType.isType(that.valueType);
        }
        return false;
    }

    @Override
    public String toString() {
        return "(type (map (key " + keyType.typeName() + ") (value " + valueType.typeName() + ")))";
    }

}
