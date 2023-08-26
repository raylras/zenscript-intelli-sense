package raylras.zen.code.type;

import raylras.zen.code.symbol.OperatorFunctionSymbol.Operator;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.SymbolFactory;

import java.util.List;
import java.util.function.UnaryOperator;

public class BoolType extends Type {

    public static final BoolType INSTANCE = new BoolType();

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public List<Symbol> getMembers() {
        return SymbolFactory.members()
                .operator(Operator.AND, this, params -> params.parameter("val", this))
                .operator(Operator.OR, this, params -> params.parameter("val", this))
                .operator(Operator.XOR, this, params -> params.parameter("val", this))
                .operator(Operator.NOT, this, UnaryOperator.identity())
                .operator(Operator.CAT, StringType.INSTANCE, params -> params.parameter("str", StringType.INSTANCE))
                .build();
    }
}
