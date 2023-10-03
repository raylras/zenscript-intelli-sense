package raylras.zen.model.symbol;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Operator {

    // unary
    NEG("-", Kind.UNARY),
    NOT("!", Kind.UNARY),
    AS("as", Kind.UNARY),
    FOR_IN("for_in", Kind.UNARY),

    // binary
    ADD("+", Kind.BINARY),
    SUB("-", Kind.BINARY),
    MUL("*", Kind.BINARY),
    DIV("/", Kind.BINARY),
    MOD("%", Kind.BINARY),
    CONCAT("~", Kind.BINARY),
    OR("|", Kind.BINARY),
    AND("&", Kind.BINARY),
    XOR("^", Kind.BINARY),
    INDEX_GET("[]", Kind.BINARY),
    RANGE("..", Kind.BINARY),
    HAS("has", Kind.BINARY),
    MEMBER_GET(".", Kind.BINARY),
    EQUALS("==", Kind.BINARY),
    NOT_EQUALS("!=", Kind.BINARY),
    LESS("<", Kind.BINARY),
    LESS_EQUALS("<=", Kind.BINARY),
    GREATER(">", Kind.BINARY),
    GREATER_EQUALS(">=", Kind.BINARY),

    // trinary
    MEMBER_SET(".=", Kind.TRINARY),
    INDEX_SET("[]=", Kind.TRINARY),

    ERROR(null, null);

    private final String literal;
    private final Kind kind;

    Operator(String literal, Kind kind) {
        this.literal = literal;
        this.kind = kind;
        if (kind != null) {
            kind.operators.put(literal, this);
        }
    }

    public static Optional<Operator> of(String literal, Kind kind) {
        return Optional.ofNullable(kind.operators.get(literal));
    }

    public static Optional<Operator> of(String literal, int paramSize) {
        return Optional.ofNullable(switch (paramSize) {
            case 0 -> Kind.UNARY;
            case 1 -> Kind.BINARY;
            case 2 -> Kind.TRINARY;
            default -> null;
        }).map(kind -> kind.getOperators().get(literal));
    }

    public String getLiteral() {
        return literal;
    }

    public Kind getKind() {
        return kind;
    }

    public enum Kind {
        UNARY, BINARY, TRINARY;

        private final Map<String, Operator> operators = new HashMap<>();

        public Map<String, Operator> getOperators() {
            return operators;
        }
    }

}
