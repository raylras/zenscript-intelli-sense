package raylras.zen.model.type;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.symbol.Operator;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolFactory;
import raylras.zen.model.symbol.SymbolProvider;

import java.util.List;

public record ArrayType(Type elementType) implements Type, SymbolProvider {

    @Override
    public String getTypeName() {
        return elementType.getTypeName() + "[]";
    }

    @Override
    public String getSimpleTypeName() {
        return elementType.getSimpleTypeName() + "[]";
    }

    @Override
    public boolean isCastableTo(Type type, CompilationEnvironment env) {
        if (type instanceof ArrayType that) {
            return this.elementType.isCastableTo(that.elementType(), env);
        }
        if (type instanceof ListType that) {
            return this.elementType.isCastableTo(that.elementType(), env);
        }
        if (type instanceof ClassType that) {
            return that.getTypeName().equals("crafttweaker.data.IData");
        }
        return Type.super.isCastableTo(type, env);
    }

    @Override
    public List<Symbol> getSymbols() {
        return SymbolFactory.builtinSymbols()
                .variable("length", IntType.INSTANCE, Symbol.Modifier.IMPLICIT_VAL)
                .operator(Operator.INDEX_GET, elementType, params -> params.parameter("index", IntType.INSTANCE))
                .operator(Operator.INDEX_SET, elementType, params -> params.parameter("index", IntType.INSTANCE).parameter("element", elementType))
                .operator(Operator.ADD, this, params -> params.parameter("element", elementType))
                .operator(Operator.FOR_IN, new ListType(elementType))
                .build();
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
