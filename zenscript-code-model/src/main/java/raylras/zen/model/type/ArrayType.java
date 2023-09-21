package raylras.zen.model.type;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.SymbolProvider;
import raylras.zen.model.symbol.Operator;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolFactory;

import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class ArrayType extends Type implements SymbolProvider {

    private final Type elementType;

    public ArrayType(Type elementType) {
        this.elementType = elementType;
    }

    public Type getElementType() {
        return elementType;
    }

    @Override
    public List<Symbol> getSymbols() {
        return SymbolFactory.builtinSymbols()
                .variable("length", IntType.INSTANCE, Symbol.Modifier.VAL)
                .operator(Operator.INDEX_GET, elementType, params -> params.parameter("index", IntType.INSTANCE))
                .operator(Operator.INDEX_SET, elementType, params ->
                        params.parameter("index", IntType.INSTANCE).parameter("element", elementType)
                )
                .operator(Operator.ADD, this, params -> params.parameter("element", elementType))
                .operator(Operator.ITERATOR, new ListType(elementType), UnaryOperator.identity())
                .build();
    }

    @Override
    public boolean isInheritedFrom(Type type) {
        if (type instanceof ArrayType that && elementType.isInheritedFrom(that.getElementType())) {
            return true;
        }
        return super.isInheritedFrom(type);
    }

    @Override
    public boolean isCastableTo(Type type, CompilationEnvironment env) {
        if (type instanceof ArrayType that && elementType.isCastableTo(that.getElementType(), env)) {
            return true;
        }
        if (type instanceof ListType that && elementType.isAssignableTo(that.getElementType(), env)) {
            return true;
        }
        if (type instanceof ClassType that && that.getSymbol().getQualifiedName().equals("crafttweaker.data.IData")) {
            return true;
        }
        return super.isCastableTo(type, env);
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
