package raylras.zen.code.tree;

import raylras.zen.code.Range;

/**
 * Represents an simple identifier.
 * e.g. "i", "foo".
 */
public class SimpleName extends Name {

    public String literal;

    public SimpleName(String literal, Range range) {
        super(range);
        this.literal = literal;
    }

    @Override
    public SimpleName getSimpleName() {
        return this;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return literal;
    }

}
