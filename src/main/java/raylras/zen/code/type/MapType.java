package raylras.zen.code.type;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.MemberProvider;
import raylras.zen.code.symbol.Operator;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.SymbolFactory;

import java.util.List;
import java.util.function.UnaryOperator;

public class MapType extends Type implements MemberProvider {

    private final Type keyType;
    private final Type valueType;

    public MapType(Type keyType, Type valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
    }

    public Type getKeyType() {
        return keyType;
    }

    public Type getValueType() {
        return valueType;
    }

    @Override
    public List<Symbol> getMembers() {
        return SymbolFactory.members()
                .variable("length", IntType.INSTANCE, Symbol.Modifier.VAL)
                .variable("keys", new ArrayType(keyType), Symbol.Modifier.VAL)
                .variable("keySet", new ArrayType(keyType), Symbol.Modifier.VAL)
                .variable("values", new ArrayType(valueType), Symbol.Modifier.VAL)
                .variable("valueSet", new ArrayType(valueType), Symbol.Modifier.VAL)
                .variable("entrySet", new ArrayType(new MapEntryType(keyType, valueType)), Symbol.Modifier.VAL)
                .operator(Operator.INDEX_GET, valueType, params -> params.parameter("key", keyType))
                .operator(Operator.INDEX_SET, VoidType.INSTANCE, params ->
                        params.parameter("key", keyType)
                                .parameter("value", valueType)
                )
                .operator(Operator.MEMBER_GET, valueType, params -> params.parameter("key", keyType))
                .operator(Operator.MEMBER_SET, VoidType.INSTANCE, params ->
                        params.parameter("key", keyType)
                                .parameter("value", valueType)
                )
                .operator(Operator.ITERATOR, this, UnaryOperator.identity())
                .build();
    }

    @Override
    public SubtypeResult isSubtypeOf(Type type, CompilationEnvironment env) {
        if (type instanceof MapType) {
            MapType that = ((MapType) type);
            SubtypeResult key = this.keyType.isSubtypeOf(that.keyType, env);
            SubtypeResult value = this.valueType.isSubtypeOf(that.valueType, env);
            return SubtypeResult.higher(key, value);
        }
        return super.isSubtypeOf(type, env);
    }

    @Override
    public String toString() {
        return valueType + "[" + keyType + "]";
    }

}
