package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Name;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;

/**
 * Represents an expression such as "expr.name".
 * e.g. "arr.length".
 */
public class MemberAccess extends Expression {

    public Expression left;
    public Name right;

    public MemberAccess(Expression left, Name right, Range range) {
        super(range);
        this.left = left;
        this.right = right;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitMemberAccess(this);
    }

    @Override
    public String toString() {
        return new Pretty().visitMemberAccess(this);
    }

}
