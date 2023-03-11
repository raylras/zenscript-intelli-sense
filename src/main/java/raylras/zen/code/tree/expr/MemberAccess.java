package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.SimpleName;
import raylras.zen.code.tree.TreeVisitor;

/**
 * Represents an expression such as "expr.name".
 * e.g. "arr.length".
 */
public class MemberAccess extends Expression {

    public Expression left;
    public SimpleName right;

    public MemberAccess(Expression left, SimpleName right, Range range) {
        super(range);
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChild(visitor, left);
            acceptChild(visitor, right);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

}
