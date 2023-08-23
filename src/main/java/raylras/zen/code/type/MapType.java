package raylras.zen.code.type;

import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.SymbolFactory;

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
        return SymbolFactory.members()
                .variable("length", IntType.INSTANCE, Symbol.Modifier.VAL)
                .variable("keys", new ArrayType(keyType), Symbol.Modifier.VAL)
                .variable("keySet", new ArrayType(keyType), Symbol.Modifier.VAL)
                .variable("values", new ArrayType(valueType), Symbol.Modifier.VAL)
                .variable("valueSet", new ArrayType(valueType), Symbol.Modifier.VAL)
                .variable("entrySet", new ArrayType(new MapEntryType(keyType, valueType)), Symbol.Modifier.VAL)
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
