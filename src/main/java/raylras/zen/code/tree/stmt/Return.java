package raylras.zen.code.tree.stmt;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
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
