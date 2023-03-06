package raylras.zen.code.tree.stmt;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;

/**
 * Represents a statement "continue".
 */
public class Continue extends Statement {

    public Continue(Range range) {
        super(range);
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitContinue(this);
    }

    @Override
    public String toString() {
        return new Pretty().visitContinue(this);
    }

}
