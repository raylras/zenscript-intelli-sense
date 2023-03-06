package raylras.zen.code.tree;

import raylras.zen.code.Range;

/**
 * Represents an identifier.
 * e.g. "i", "foo".
 */
public class Name extends TreeNode {

    public String literal;

    public Name(String literal, Range range) {
        super(range);
        this.literal = literal;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitName(this);
    }

    @Override
    public String toString() {
        return literal;
    }

}
