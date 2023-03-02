package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.TreeVisitor;

/**
 * Represents an expression "super".
 */
public class Super extends Expression {

    public Super(Range range) {
        super(range);
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitSuper(this);
    }

}
