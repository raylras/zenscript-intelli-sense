package raylras.zen.model.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node

data class UnaryExpression(
    val operator: UnaryOperator,
    val expression: Expression,
) : Node(), Expression

enum class UnaryOperator(val text: String) {
    NOT("!"),
    NEG("-");

    companion object {
        fun fromString(s: String): UnaryOperator = when (s) {
            "!" -> NOT
            "-" -> NEG
            else -> throw IllegalArgumentException("Invalid unary operator: $s")
        }
    }
}
