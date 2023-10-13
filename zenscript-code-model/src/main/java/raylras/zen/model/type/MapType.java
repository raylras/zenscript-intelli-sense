package raylras.zen.model.type;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.symbol.Operator;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolFactory;
import raylras.zen.model.symbol.SymbolProvider;

import java.util.List;

public record MapType(Type keyType, Type valueType) implements Type, SymbolProvider {

    @Override
    public String getTypeName() {
        return valueType.getTypeName() + "[" + keyType.getTypeName() + "]";
    }

    @Override
    public String getSimpleTypeName() {
        return valueType.getSimpleTypeName() + "[" + keyType.getSimpleTypeName() + "]";
    }

    @Override
    public boolean isCastableTo(Type type, CompilationEnvironment env) {
        if (type instanceof MapType that) {
            return this.keyType.isCastableTo(that.keyType, env)
                    && this.valueType.isCastableTo(that.valueType, env);
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
                .variable("keys", new ArrayType(keyType), Symbol.Modifier.IMPLICIT_VAL)
                .variable("keySet", new ArrayType(keyType), Symbol.Modifier.IMPLICIT_VAL)
                .variable("values", new ArrayType(valueType), Symbol.Modifier.IMPLICIT_VAL)
                .variable("valueSet", new ArrayType(valueType), Symbol.Modifier.IMPLICIT_VAL)
                .variable("entrySet", new ArrayType(new MapEntryType(keyType, valueType)), Symbol.Modifier.IMPLICIT_VAL)
                .operator(Operator.INDEX_GET, valueType, params -> params.parameter("key", keyType))
                .operator(Operator.INDEX_SET, VoidType.INSTANCE, params -> params.parameter("key", keyType).parameter("value", valueType))
                .operator(Operator.MEMBER_GET, valueType, params -> params.parameter("key", keyType))
                .operator(Operator.MEMBER_SET, VoidType.INSTANCE, params -> params.parameter("key", keyType).parameter("value", valueType))
                .operator(Operator.FOR_IN, this)
                .build();
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
