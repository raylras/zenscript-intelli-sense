package raylras.zen.code.type;

import raylras.zen.code.symbol.OperatorFunctionSymbol;
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
                .operator(OperatorFunctionSymbol.Operator.INDEX_GET, elementType, params -> params.parameter("index", IntType.INSTANCE))
                .operator(OperatorFunctionSymbol.Operator.INDEX_SET, elementType, params ->
                        params.parameter("index", IntType.INSTANCE).parameter("element", elementType)
                )
                .operator(OperatorFunctionSymbol.Operator.ADD, this, params -> params.parameter("element", elementType))
                .build();
    }

    @Override
    public SubtypeResult isSubtypeOf(Type type) {
        if (this.equals(type)) {
            return SubtypeResult.SELF;
        }
        if (type == AnyType.INSTANCE) {
            return SubtypeResult.INHERIT;
        }
        if (type instanceof ArrayType) {
            ArrayType that = (ArrayType) type;
            return this.elementType.isSubtypeOf(that.getElementType());
        }
        if (type instanceof ListType) {
            ListType that = (ListType) type;
            return SubtypeResult.higher(this.elementType.isSubtypeOf(that.getElementType()), SubtypeResult.CASTER);
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
