package raylras.zen.code.tree.stmt;

import raylras.zen.code.Range;
import raylras.zen.code.tree.TreeVisitor;

/**
 * Represents a statement "break".
 */
public class Break extends Statement {

    public Break(Range range) {
        super(range);
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitBreak(this);
    }

}
