package raylras.zen.model.type;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.symbol.SymbolProvider;
import raylras.zen.model.symbol.Operator;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolFactory;

import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class ListType extends Type implements SymbolProvider<Symbol> {

    private final Type elementType;

    public ListType(Type elementType) {
        this.elementType = elementType;
    }

    public Type getElementType() {
        return elementType;
    }

    @Override
    public List<Symbol> getSymbols() {
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
