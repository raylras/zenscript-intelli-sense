package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;

/**
 * Represents a binary expression such as "expr op expr".
 * e.g. "i + j", "i <= 0".
 */
public class Binary extends Expression {

    public Expression left;
    public Expression right;
    public Operator op;

    public Binary(Expression left, Expression right, Operator op, Range range) {
        super(range);
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChild(visitor, left);
            acceptChild(visitor, right);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

    public static class Operator {
        public static final Operator ADD = new Operator("+");
        public static final Operator SUB = new Operator("-");
        public static final Operator MUL = new Operator("*");
        public static final Operator DIV = new Operator("/");
        public static final Operator MOD = new Operator("%");
        public static final Operator CAT = new Operator("~");
        public static final Operator LESS = new Operator("<");
        public static final Operator GREATER = new Operator(">");
        public static final Operator LESS_EQUALS = new Operator("<=");
        public static final Operator GREATER_EQUALS = new Operator(">=");
        public static final Operator BITWISE_AND = new Operator("&");
        public static final Operator BITWISE_OR = new Operator("|");
        public static final Operator LOGICAL_AND = new Operator("&&");
        public static final Operator LOGICAL_OR = new Operator("||");
        public static final Operator EQUALS = new Operator("==");
        public static final Operator NOT_EQUALS = new Operator("!=");
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

}
