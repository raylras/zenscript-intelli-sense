package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;
import raylras.zen.code.type.Type;

/**
 * A constant value given literally.
 * e.g. "null", "true", "1.0", "'str'".
 */
public class ConstantExpr extends Expression {

    public Object value;
    public Type.Tag tag;

    public ConstantExpr(Object value, Type.Tag tag, Range range) {
        super(range);
        this.value = value;
        this.tag = tag;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitConstantExpr(this);
    }

    @Override
    public String toString() {
        return new Pretty().visitConstantExpr(this);
    }

}
