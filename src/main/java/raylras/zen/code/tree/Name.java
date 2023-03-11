package raylras.zen.code.tree;

import raylras.zen.code.Range;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.tree.expr.Expression;

public abstract class Name extends Expression {

    public Symbol symbol;

    public Name(Range range) {
        super(range);
    }

    public abstract SimpleName getSimpleName();

}
