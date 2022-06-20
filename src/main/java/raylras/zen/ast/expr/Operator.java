package raylras.zen.ast.expr;

import java.util.Arrays;
import java.util.List;

public final class Operator {

    public enum Unary {
        LOGIC_NOT("!"), NEG("-");

        private final String op;
        Unary(String op) {
            this.op = op;
        }
        @Override
        public String toString() {
            return op;
        }
    }

    public enum Binary {
        // Arithmetic
        ADD("+"), SUB("-"), MUL("*"), DIV("/"), MOD("%"),

        // Relational
        EQUAL("=="), NOT_EQUAL("!="), LESS("<"), GREATER(">"),  LESS_EQUAL("<="), GREATER_EQUAL(">="), INSTANCEOF("instanceof"),

        // Bitwise
        BIT_AND("&"), BIT_OR("|"), BIT_XOR("^"),

        // Logical
        LOGIC_OR("||"), LOGIC_AND("&&"),

        // Other
        CAT("~"), IN("in");

        private final String op;
        Binary(String op) {
            this.op = op;
        }

        public static List<Binary> getComparisons() {
            return Arrays.asList(EQUAL, NOT_EQUAL, LESS, GREATER, LESS_EQUAL, GREATER_EQUAL);
        }

        @Override
        public String toString() {
            return op;
        }
    }

    public enum Assignment {
        ASSIGN("="), ADD_ASSIGN("+="), SUB_ASSIGN("-="), MUL_ASSIGN("*="), DIV_ASSIGN("/="),
        AND_ASSIGN("&="), OR_ASSIGN("|="), XOR_ASSIGN("^="), MOD_ASSIGN("%="), CAT_ASSIGN("~=");
        private final String op;
        Assignment(String op) {
            this.op = op;
        }
        @Override
        public String toString() {
            return op;
        }
    }

    public static Operator.Unary getUnary(String operator) {
        for (Unary unary : Unary.values()) {
            if (unary.op.equals(operator)) return unary;
        }
        return null;
    }

    public static Operator.Binary getBinary(String operator) {
        for (Binary binary : Binary.values()) {
            if (binary.op.equals(operator)) return binary;
        }
        if ("has".equals(operator)) {
            return Binary.IN;
        }
        return null;
    }

    public static Operator.Assignment getAssignment(String operator) {
        for (Assignment assignment : Assignment.values()) {
            if (assignment.op.equals(operator)) return assignment;
        }
        return null;
    }

}
