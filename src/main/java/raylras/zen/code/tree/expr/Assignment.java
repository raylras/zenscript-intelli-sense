package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;

/**
 * Represents an expression such as "name op expr".
 * e.g. "i += 1", "i = i + 1".
 */
public class Assignment extends Expression {

    public static class Operator {
        public static final Operator ASSIGN = new Operator("=");
        public static final Operator ADD_ASSIGN = new Operator("+=");
        public static final Operator SUB_ASSIGN = new Operator("-=");
        public static final Operator MUL_ASSIGN = new Operator("*=");
        public static final Operator DIV_ASSIGN = new Operator("/=");
        public static final Operator MOD_ASSIGN = new Operator("%=");
        public static final Operator INVALID = new Operator("INVALID");

        public final String literal;
        private Operator(String literal) {
            this.literal = literal;
        }
        @Override
        public String toString() {
            return literal;
        }
    }

    public Expression left;
    public Expression right;
    public Operator op;

    public Assignment(Expression left, Expression right, Operator op, Range range) {
        super(range);
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitAssignment(this);
    }

    @Override
    public String toString() {
        return new Pretty().visitAssignment(this);
    }

}
