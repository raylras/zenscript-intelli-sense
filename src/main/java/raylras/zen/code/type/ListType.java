package raylras.zen.code.type;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.MemberProvider;
import raylras.zen.code.symbol.Operator;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.SymbolFactory;

import java.util.List;
import java.util.function.UnaryOperator;

public class ListType extends Type implements MemberProvider {

    private final Type elementType;

    public ListType(Type elementType) {
        this.elementType = elementType;
    }

    public Type getElementType() {
        return elementType;
    }

    @Override
    public List<Symbol> getMembers() {
        return SymbolFactory.members()
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
    public SubtypeResult isSubtypeOf(Type type, CompilationEnvironment env) {
        if (this.equals(type)) {
            return SubtypeResult.SELF;
        }
        if (type == AnyType.INSTANCE) {
            return SubtypeResult.INHERIT;
        }
        if (type instanceof ArrayType) {
            ArrayType that = (ArrayType) type;
            return SubtypeResult.higher(this.elementType.isSubtypeOf(that.getElementType(), env), SubtypeResult.CASTER);
        }
        if (type instanceof ListType) {
            ListType that = (ListType) type;
            return this.elementType.isSubtypeOf(that.getElementType(), env);
        }
        return super.isSubtypeOf(type, env);
    }

    @Override
    public String toString() {
        return "[" + elementType + "]";
    }

}
