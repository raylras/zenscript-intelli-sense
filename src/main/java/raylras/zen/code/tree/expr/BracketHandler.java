package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.TreeVisitor;

/**
 * Represents an expression such as "&lt;string&gt;".
 * e.g. &lt;foo:bar:3&gt;".
 */
public class BracketHandler extends Expression {

    public String literal;

    public BracketHandler(String literal, Range range) {
        super(range);
        this.literal = literal;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitBracketHandler(this);
    }

}
