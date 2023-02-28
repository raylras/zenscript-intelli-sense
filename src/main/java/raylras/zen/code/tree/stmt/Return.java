package raylras.zen.code.tree.stmt;

import raylras.zen.code.Range;
import raylras.zen.code.tree.TreeVisitor;
import raylras.zen.code.tree.expr.Expression;

/**
 * Represents a statement such as "return expr;".
 * e.g. "return;", "return true;".
 */
public class Return extends Statement {

    public Expression expr;

    public Return(Expression expr, Range range) {
        super(range);
        this.expr = expr;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitReturn(this);
    }

}
