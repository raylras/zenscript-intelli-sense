package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;

import java.util.List;

/**
 * Represents an expression such as "[expr, ...]".
 * e.g. "[1,2,3]".
 */
public class ArrayLiteral extends Expression {

    public List<Expression> elements;

    public ArrayLiteral(List<Expression> elements, Range range) {
        super(range);
        this.elements = elements;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitArrayLiteral(this);
    }

    @Override
    public String toString() {
        return new Pretty().visitArrayLiteral(this);
    }

}
