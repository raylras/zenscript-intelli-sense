package raylras.zen.code.type;

import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.SymbolFactory;

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
        return SymbolFactory.members()
                .variable("key", keyType, Symbol.Modifier.VAL)
                .variable("value", valueType, Symbol.Modifier.VAL)
                .build();
    }

    @Override
    public String toString() {
        return "Map.Entry<" + keyType + "," + valueType + ">";
    }

}
