package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;

import java.util.List;

/**
 * Represents an expression such as "expr(expr, ...)".
 * e.g. "add(1, 2)".
 */
public class Call extends Expression {

    public Expression left;
    public List<Expression> args;

    public Call(Expression left, List<Expression> args, Range range) {
        super(range);
        this.left = left;
        this.args = args;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitCall(this);
    }

    @Override
    public String toString() {
        return new Pretty().visitCall(this);
    }

}
