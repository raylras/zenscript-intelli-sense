package raylras.zen.code.type;

import raylras.zen.code.symbol.BuiltinSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.TypeMatchingResult;

import java.util.List;
import java.util.Objects;

public class ArrayType extends Type implements IDataCastable {

    private final Type elementType;

    public ArrayType(Type elementType) {
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
                .build();
    }

    @Override
    protected TypeMatchingResult applyCastRules(Type to) {
        if (to instanceof ArrayType) {
            return getElementType().applyCastRules(((ArrayType) to).getElementType());
        }
        if (to instanceof ListType) {
            return getElementType().applyCastRules(((ListType) to).getElementType()).min(TypeMatchingResult.CASTER);
        }
        return TypeMatchingResult.INVALID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayType arrayType = (ArrayType) o;
        return Objects.equals(elementType, arrayType.elementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elementType);
    }

    @Override
    public String toString() {
        return elementType + "[]";
    }

}
