package raylras.zen.model.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node

data class BinaryExpression(
    val left: Expression,
    val operator: BinaryOperator,
    val right: Expression,
) : Node(), Expression

enum class BinaryOperator(val text: String) {
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
        fun fromString(s: String): BinaryOperator = entries
            .firstOrNull { it.text == s }
            ?: throw IllegalArgumentException("Invalid binary operator: $s")
    }
}
