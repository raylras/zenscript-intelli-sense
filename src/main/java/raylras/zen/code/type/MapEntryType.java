package raylras.zen.code.type;

import raylras.zen.code.symbol.BuiltinSymbol;
import raylras.zen.code.symbol.Symbol;

import java.util.List;

public class MapEntryType extends Type {

    private final Type keyType;
    private final Type valueType;

    public MapEntryType(Type keyType, Type valueType) {
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
                .add("key", keyType)
                .add("value", valueType)
                .build();
    }

    @Override
    public String toString() {
        return "Map.Entry<" + keyType + "," + valueType + ">";
    }

}
