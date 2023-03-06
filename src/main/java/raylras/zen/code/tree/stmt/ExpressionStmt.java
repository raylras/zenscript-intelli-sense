package raylras.zen.code.tree.stmt;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;
import raylras.zen.code.tree.expr.Expression;

/**
 * Represents a statement such as "expr;".
 * e.g. "foo();", "i += 1;".
 */
public class ExpressionStmt extends Statement {

    public Expression expr;

    public ExpressionStmt(Expression expr, Range range) {
        super(range);
        this.expr = expr;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitExpressionStmt(this);
    }

    @Override
    public String toString() {
        return new Pretty().visitExpressionStmt(this);
    }

}
