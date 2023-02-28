package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.TreeVisitor;

public class MapEntry extends Expression {

    public Expression key;
    public Expression value;

    public MapEntry(Expression key, Expression value, Range range) {
        super(range);
        this.key = key;
        this.value = value;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitMapEntry(this);
    }

}
