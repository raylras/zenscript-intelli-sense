package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;

/**
 * Represents an expression such as "(expr)".
 * e.g. "(1 + 2)".
 */
public class Parens extends Expression {

    public Expression expr;

    public Parens(Expression expr, Range range) {
        super(range);
        this.expr = expr;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitParens(this);
    }

    @Override
    public String toString() {
        return new Pretty().visitParens(this);
    }

}
