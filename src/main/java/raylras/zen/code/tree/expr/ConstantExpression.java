package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.TreeVisitor;
import raylras.zen.code.type.Type;

/**
 * A constant value given literally.
 * e.g. "null", "true", "1.0", "'str'".
 */
public class ConstantExpression extends Expression {

    public Object value;
    public Type.Kind kind;

    public ConstantExpression(Object value, Type.Kind kind, Range range) {
        super(range);
        this.value = value;
        this.kind = kind;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
