package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;

/**
 * Represents an expression such as "expr .. expr".
 * e.g. "0 to 5", "0 .. arr.length()".
 */
public class IntRange extends Expression {

    public Expression from;
    public Expression to;

    public IntRange(Expression from, Expression to, Range range) {
        super(range);
        this.from = from;
        this.to = to;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChild(visitor, from);
            acceptChild(visitor, to);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

}
