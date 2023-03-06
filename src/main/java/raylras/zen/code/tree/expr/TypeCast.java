package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;
import raylras.zen.code.tree.TypeLiteral;

/**
 * Represents an expression such as "expr as type".
 * e.g. "0.1 as double", "arr as any".
 */
public class TypeCast extends Expression {

    public Expression expr;
    public TypeLiteral type;

    public TypeCast(Expression expr, TypeLiteral type, Range range) {
        super(range);
        this.expr = expr;
        this.type = type;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitTypeCast(this);
    }

    @Override
    public String toString() {
        return new Pretty().visitTypeCast(this);
    }

}
