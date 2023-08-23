package raylras.zen.code.type;

import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.SymbolFactory;

import java.util.List;

public class ListType extends Type {

    private final Type elementType;

    public ListType(Type elementType) {
        this.elementType = elementType;
    }

    public Type getElementType() {
        return elementType;
    }

    @Override
    public List<Symbol> getMembers() {
        return SymbolFactory.members()
                .variable("length", IntType.INSTANCE, Symbol.Modifier.VAL)
                .function("remove", VoidType.INSTANCE, params ->
                        params.parameter("index", IntType.INSTANCE, false, false))
                .build();
    }

    @Override
    public String toString() {
        return "[" + elementType + "]";
    }

}
