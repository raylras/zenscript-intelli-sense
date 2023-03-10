package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;

/**
 * Represents an expression such as "expr[expr]".
 * e.g. "arr[i]".
 */
public class ArrayAccess extends Expression {

    public Expression left;
    public Expression index;

    public ArrayAccess(Expression left, Expression index, Range range) {
        super(range);
        this.left = left;
        this.index = index;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            left.accept(visitor);
            index.accept(visitor);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

}
