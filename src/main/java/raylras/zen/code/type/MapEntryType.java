package raylras.zen.code.type;

public class MapEntryType extends Type {
    public final Type keyType;
    public final Type valueType;

    public MapEntryType(Type keyType, Type valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
    }

    @Override
    public Kind getKind() {
        return Kind.MAP_ENTRY;
    }

    @Override
    public String toString() {
        return "Map.Entry(" + keyType + ", " + valueType + ")";
    }


}
