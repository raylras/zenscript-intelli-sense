package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;

import java.util.List;

/**
 * Represents an expression such as "{expr: expr, ...}".
 * e.g. "{'key': 'value', foo: 'FOO'}".
 */
public class MapLiteral extends Expression {

    public List<MapEntry> entries;

    public MapLiteral(List<MapEntry> entries, Range range) {
        super(range);
        this.entries = entries;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChildren(visitor, entries);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

}
