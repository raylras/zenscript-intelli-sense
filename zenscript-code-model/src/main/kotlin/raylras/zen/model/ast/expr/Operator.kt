package raylras.zen.model.ast.expr

sealed interface Operator {
    val token: String
}

enum class UnaryOperator(override val token: String) : Operator {
    NOT("!"),
    NEG("-");

    companion object {
        fun fromString(s: String): UnaryOperator =
            entries.find { it.token == s } ?: throw NoSuchElementException("Invalid unary operator: $s")
    }
}

enum class BinaryOperator(override val token: String) : Operator {
    // arithmetic
    PLUS("+"),
    MINUS("-"),
    TIMES("*"),
    DIV("/"),
    MOD("%"),

    // logical
    AND("&&"),
    OR("||"),

    // comparison
    EQ("=="),
    NEQ("!="),
    LT("<"),
    GT(">"),
    LEQ("<="),
    GEQ(">="),

    // bitwise
    BIT_AND("&"),
    BIT_OR("|"),
    BIT_XOR("^"),

    // assignment
    ASSIGN("="),
    PLUS_ASSIGN("+="),
    MINUS_ASSIGN("-="),
    TIMES_ASSIGN("*="),
    DIV_ASSIGN("/="),
    MOD_ASSIGN("%="),
    BIT_AND_ASSIGN("&="),
    BIT_OR_ASSIGN("|="),
    BIT_XOR_ASSIGN("^="),
    CONCAT_ASSIGN("~="),

    // other
    IN("in"),
    HAS("has"),
    CONCAT("~");

    companion object {
        fun fromString(s: String): BinaryOperator =
            entries.find { it.token == s } ?: throw NoSuchElementException("Invalid binary operator: $s")
    }
}
