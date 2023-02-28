package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.TreeVisitor;

/**
 * Represents an expression "this".
 */
public class This extends Expression {

    public This(Range range) {
        super(range);
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitThis(this);
    }

}
