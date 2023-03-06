package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;

/**
 * Represents an expression such as "expr ? expr : expr".
 * e.g. "i < j ? i : j".
 */
public class Ternary extends Expression {

    public Expression condition;
    public Expression truePart;
    public Expression falsePart;

    public Ternary(Expression condition, Expression truePart, Expression falsePart, Range range) {
        super(range);
        this.condition = condition;
        this.truePart = truePart;
        this.falsePart = falsePart;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitTernary(this);
    }

    @Override
    public String toString() {
        return new Pretty().visitTernary(this);
    }

}
