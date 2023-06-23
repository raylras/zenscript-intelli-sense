package raylras.zen.code.type;

import raylras.zen.code.symbol.BuiltinSymbol;
import raylras.zen.code.symbol.Symbol;

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
        // Members that cannot be represented by zenscript are represented as built-in symbols
        return BuiltinSymbol.List.builder()
                .add("length", IntType.INSTANCE)
                .add("remove", new FunctionType(elementType, IntType.INSTANCE))
                .build();
    }

    @Override
    public String toString() {
        return "[" + elementType + "]";
    }

}
