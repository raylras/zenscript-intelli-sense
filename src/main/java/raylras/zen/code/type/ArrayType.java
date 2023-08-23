package raylras.zen.code.type;

import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.SymbolFactory;

import java.util.List;
import java.util.Objects;

public class ArrayType extends Type {

    private final Type elementType;

    public ArrayType(Type elementType) {
        this.elementType = elementType;
    }

    public Type getElementType() {
        return elementType;
    }

    @Override
    public List<Symbol> getMembers() {
        return SymbolFactory.members()
                .variable("length", IntType.INSTANCE, Symbol.Modifier.VAL)
                .build();
    }

    @Override
    public SubtypeResult isSubtypeOf(Type type) {
        if (type == AnyType.INSTANCE) {
            return SubtypeResult.INHERIT;
        }
        if (type instanceof ArrayType) {
            ArrayType that = (ArrayType) type;
            return this.elementType.isSubtypeOf(that.getElementType());
        }
        if (type instanceof ListType) {
            ListType that = (ListType) type;
            return this.elementType.isSubtypeOf(that.getElementType());
        }
        return super.isSubtypeOf(type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayType arrayType = (ArrayType) o;
        return Objects.equals(elementType, arrayType.elementType);
    }

    @Override
    public String toString() {
        return elementType + "[]";
    }

}
