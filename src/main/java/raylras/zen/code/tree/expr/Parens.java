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
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChild(visitor, expr);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

}
