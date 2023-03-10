package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
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
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChild(visitor, key);
            acceptChild(visitor, value);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

}
