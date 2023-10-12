package raylras.zen.model.type;

import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolFactory;
import raylras.zen.model.symbol.SymbolProvider;

import java.util.List;

public record MapEntryType(Type keyType, Type valueType) implements Type, SymbolProvider {

    @Override
    public String getTypeName() {
        return "Map.Entry<" + keyType.getTypeName() + "," + valueType.getTypeName() + ">";
    }

    @Override
    public String getSimpleTypeName() {
        return "Map.Entry<" + keyType.getSimpleTypeName() + "," + valueType.getSimpleTypeName() + ">";
    }

    @Override
    public List<Symbol> getSymbols() {
        return SymbolFactory.builtinSymbols()
                .variable("key", keyType, Symbol.Modifier.IMPLICIT_VAL)
                .variable("value", valueType, Symbol.Modifier.IMPLICIT_VAL)
                .build();
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
