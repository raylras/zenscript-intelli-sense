package raylras.zen.code.type;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.MemberProvider;
import raylras.zen.code.symbol.Operator;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.SymbolFactory;

import java.util.List;

public class StringType extends Type implements MemberProvider {

    public static final StringType INSTANCE = new StringType();

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public SubtypeResult isSubtypeOf(Type type, CompilationEnvironment env) {
        if (this.equals(type)) {
            return SubtypeResult.SELF;
        }
        if (type instanceof NumberType || BoolType.INSTANCE.equals(type)) {
            return SubtypeResult.CASTER;
        }
        return super.isSubtypeOf(type, env);
    }

    @Override
    public List<Symbol> getMembers() {
        //FIXME: read string.dzs
        return SymbolFactory.members()
                .operator(Operator.ADD, this, params -> params.parameter("str", this))
                .operator(Operator.CAT, this, params -> params.parameter("str", this))
                .operator(Operator.INDEX_GET, this, params -> params.parameter("index", IntType.INSTANCE))
                .operator(Operator.COMPARE, IntType.INSTANCE, params -> params.parameter("str", this))
                .operator(Operator.HAS, BoolType.INSTANCE, params -> params.parameter("str", this))
                .build();
    }
}
