package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;

/**
 * Represents a unary expression such as "op expr".
 * e.g. "!isEmpty()", "-i".
 */
public class Unary extends Expression {

    public Expression expr;
    public Operator op;

    public Unary(Expression expr, Operator op, Range range) {
        super(range);
        this.expr = expr;
        this.op = op;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChild(visitor, expr);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

    public static class Operator {
        public static final Operator NOT = new Operator("!");
        public static final Operator POS = new Operator("+");
        public static final Operator NEG = new Operator("-");
        public static final Operator INVALID = new Operator("INVALID");

        public final String literal;

        public Operator(String literal) {
            this.literal = literal;
        }

        @Override
        public String toString() {
            return literal;
        }
    }

}
