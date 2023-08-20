package raylras.zen.code.symbol;

public enum OverloadingOperator {
    ADD("+"),
    SUB("-"),
    MUL("*"),
    DIV("/"),
    MOD("%"),
    CAT("~"),
    OR("|"),
    AND("&"),
    XOR("^"),
    NEG("-"),
    NOT("!"),
    INDEX_GET("[]"),
    INDEX_SET("[]="),
    INT_RANGE(".."),
    HAS("has"),
    COMPARE("compare"),
    MEMBER_GET("."),
    MEMBER_SET(".="),
    EQUALS("=="),
    AS("as"),
    ITERATOR("iterator"),
    ;

    public final String literal;

    OverloadingOperator(String literal) {
        this.literal = literal;
    }
}
