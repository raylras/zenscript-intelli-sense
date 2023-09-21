package raylras.zen.model.symbol;

import java.util.HashMap;
import java.util.Map;

public enum Operator {
    ADD("+", OperatorType.BINARY),
    SUB("-", OperatorType.BINARY),
    MUL("*", OperatorType.BINARY),
    DIV("/", OperatorType.BINARY),
    MOD("%", OperatorType.BINARY),
    CAT("~", OperatorType.BINARY),
    OR("|", OperatorType.BINARY),
    AND("&", OperatorType.BINARY),
    XOR("^", OperatorType.BINARY),
    NEG("-", OperatorType.UNARY),
    NOT("!", OperatorType.UNARY),
    INDEX_GET("[]", OperatorType.BINARY),
    INDEX_SET("[]=", OperatorType.TRINARY),
    RANGE("..", OperatorType.BINARY),
    HAS("has", OperatorType.BINARY),
    COMPARE("compare", OperatorType.BINARY),
    MEMBER_GET(".", OperatorType.BINARY),
    MEMBER_SET(".=", OperatorType.TRINARY),
    EQUALS("==", OperatorType.BINARY),
    AS("as", OperatorType.UNARY),
    ITERATOR("iterator", OperatorType.UNARY),
    ERROR;

    private final String literal;

    private final OperatorType operatorType;

    Operator(String literal, OperatorType operatorType) {
        this.literal = literal;
        this.operatorType = operatorType;
        operatorType.operators.put(literal, this);
    }

    Operator() {
        this.literal = "";
        this.operatorType = null;
    }

    public String getLiteral() {
        return literal;
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }

    public enum OperatorType {
        UNARY,
        BINARY,
        TRINARY;

        private final Map<String, Operator> operators = new HashMap<>();

        public Map<String, Operator> getOperators() {
            return operators;
        }
    }
}
