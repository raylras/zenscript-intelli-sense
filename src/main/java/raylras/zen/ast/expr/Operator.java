package raylras.zen.ast.expr;

public enum Operator {

    ADD("+"),
    SUB("-"),
    MUL("*"),
    DIV("/"),
    MOD("%"),
    EQUAL("=="),
    NOT_EQUAL("!="),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUAL("<="),
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUAL(">="),
    INSTANCEOF("instanceof"),
    AND("&&"),
    OR("||"),
    BITWISE_AND("&"),
    BITWISE_OR("|"),
    BITWISE_XOR("^"),
    ASSIGN("="),
    ADD_ASSIGN("+="),
    SUB_ASSIGN("-="),
    MUL_ASSIGN("*="),
    DIV_ASSIGN("/="),
    MOD_ASSIGN("%="),
    BITWISE_AND_ASSIGN("&="),
    BITWISE_OR_ASSIGN("|="),
    BITWISE_XOR_ASSIGN("^="),
    STRING_CONNECT_ASSIGN("~="),
    STRING_CONNECT("~"),
    IN("in"),
    HAS("has"),
    INVALID("INVALID");
    private final String value;

    Operator(String value) {
        this.value = value;
    }

    public static Operator of(String literal) {
        for (Operator operator : Operator.values()) {
            if (operator.toString().equals(literal)) {
                return operator;
            }
        }
        return INVALID;
    }

    @Override
    public String toString() {
        return value;
    }

}
