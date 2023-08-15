package raylras.zen.code.type;

import raylras.zen.code.symbol.BuiltinSymbol;
import raylras.zen.code.symbol.Symbol;

import java.util.List;

public class MapType extends Type {

    private final Type keyType;
    private final Type valueType;

    public MapType(Type keyType, Type valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
    }

    public Type getKeyType() {
        return keyType;
    }

    public Type getValueType() {
        return valueType;
    }

    @Override
    public List<Symbol> getMembers() {
        // Members that cannot be represented by zenscript are represented as built-in symbols
        return BuiltinSymbol.List.builder()
                .add("length", IntType.INSTANCE)
                .add("keys", new ArrayType(keyType))
                .add("keySet", new ArrayType(keyType))
                .add("values", new ArrayType(valueType))
                .add("valueSet", new ArrayType(valueType))
                .add("entrySet", new ArrayType(new MapEntryType(keyType, valueType)))
                .build();
    }

    @Override
    public SubtypeResult isSubtypeOf(Type type) {
        if (type instanceof MapType) {
            MapType that = ((MapType) type);
            SubtypeResult key = this.keyType.isSubtypeOf(that.keyType);
            SubtypeResult value = this.valueType.isSubtypeOf(that.valueType);
            return SubtypeResult.higher(key, value);
        }
        return super.isSubtypeOf(type);
    }

    @Override
    public String toString() {
        return valueType + "[" + keyType + "]";
    }

}
