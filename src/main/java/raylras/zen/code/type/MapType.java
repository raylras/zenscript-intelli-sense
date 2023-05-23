package raylras.zen.code.type;

public class MapType extends Type {

    public Type keyType;
    public Type valueType;

    public MapType(Type keyType, Type valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
    }

    public MapEntryType getEntryType() {
        return new MapEntryType(keyType, valueType);
    }

    @Override
    public Kind getKind() {
        return Kind.MAP;
    }

    @Override
    public String toString() {
        return valueType + "[" + keyType + "]";
    }

}
