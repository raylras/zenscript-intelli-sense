package raylras.zen.model.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node

interface LiteralExpression : Expression
interface NumberLiteral : LiteralExpression

enum class Radix {
    BINARY,
    DECIMAL,
    HEXADECIMAL;

    companion object {
        fun fromString(s: String): Radix = when {
            s.startsWith("0B", ignoreCase = true) -> BINARY
            s.startsWith("0X", ignoreCase = true) -> HEXADECIMAL
            else -> DECIMAL
        }
    }
}

fun String.toRadix(): Int = when {
    this.startsWith("0B", ignoreCase = true) -> 2
    this.startsWith("0X", ignoreCase = true) -> 16
    else -> 10
}

data class BoolLiteral(
    val value: Boolean,
) : LiteralExpression, Node()

data class IntLiteral(
    val value: Int,
    val radix: Int = 10,
) : NumberLiteral, Node()

data class LongLiteral(
    val value: Long,
    val radix: Int = 10,
) : NumberLiteral, Node()

data class FloatLiteral(
    val value: Float,
) : NumberLiteral, Node()

data class DoubleLiteral(
    val value: Double,
) : NumberLiteral, Node()

data class StringLiteral(
    val value: String,
) : LiteralExpression, Node()

class NullLiteral : LiteralExpression, Node()

data class ArrayLiteral(
    val elements: List<Expression> = emptyList(),
) : LiteralExpression, Node()

data class MapLiteral(
    val entries: List<MapEntry> = emptyList(),
) : LiteralExpression, Node()

data class MapEntry(
    val key: Expression,
    val value: Expression,
) : Node()
