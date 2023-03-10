package raylras.zen.code.tree.stmt;

import raylras.zen.code.Range;
import raylras.zen.code.tree.TreeVisitor;

/**
 * Represents a statement "continue".
 */
public class Continue extends Statement {

    public Continue(Range range) {
        super(range);
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return "continue ";
    }

}
