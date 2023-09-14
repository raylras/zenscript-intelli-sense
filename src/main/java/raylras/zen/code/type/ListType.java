package raylras.zen.code.type;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.SymbolProvider;
import raylras.zen.code.symbol.Operator;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.SymbolFactory;
import raylras.zen.code.symbol.SymbolGroup;

import java.util.Objects;
import java.util.function.UnaryOperator;

public class ListType extends Type implements SymbolProvider {

    private final Type elementType;

    public ListType(Type elementType) {
        this.elementType = elementType;
    }

    public Type getElementType() {
        return elementType;
    }

    @Override
    public SymbolGroup getSymbols() {
        return SymbolFactory.builtinSymbols()
                .variable("length", IntType.INSTANCE, Symbol.Modifier.VAL)
                .function("remove", VoidType.INSTANCE, params -> params.parameter("index", IntType.INSTANCE))
                .operator(Operator.INDEX_GET, elementType, params -> params.parameter("index", IntType.INSTANCE))
                .operator(Operator.INDEX_SET, elementType, params ->
                        params.parameter("index", IntType.INSTANCE).parameter("element", elementType)
                )
                .operator(Operator.ADD, this, params -> params.parameter("element", elementType))
                .operator(Operator.ITERATOR, this, UnaryOperator.identity())
                .build();
    }

    @Override
    public boolean isInheritedFrom(Type type) {
        if (type instanceof ListType that && elementType.isInheritedFrom(that.getElementType())) {
            return true;
        }
        return super.isInheritedFrom(type);
    }

    @Override
    public boolean isCastableTo(Type type, CompilationEnvironment env) {
        if (type instanceof ListType that && elementType.isCastableTo(that.getElementType(), env)) {
            return true;
        }
        if (type instanceof ArrayType that && elementType.isAssignableTo(that.getElementType(), env)) {
            return true;
        }
        return super.isCastableTo(type, env);
    }

    @Override
    public String toString() {
        return "[" + elementType + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListType symbols = (ListType) o;
        return Objects.equals(elementType, symbols.elementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toString());
    }
}
