package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
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
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitMapLiteral(this);
    }

}
