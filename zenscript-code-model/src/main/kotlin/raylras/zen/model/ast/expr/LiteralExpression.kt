package raylras.zen.model.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node

interface LiteralExpression : Expression
interface NumberLiteral : LiteralExpression

fun String.toRadix(): Int = when {
    startsWith("0B", ignoreCase = true) -> 2
    startsWith("0X", ignoreCase = true) -> 16
    else -> 10
}

fun String.asDigits(): String = trimPrefix().trimSuffix()

private fun String.trimPrefix(): String = when (toRadix()) {
    2, 16 -> drop(2)
    else -> this
}

private fun String.trimSuffix(): String = when(last()) {
    'l', 'L', 'f', 'F', 'd', 'D' -> dropLast(1)
    else -> this
}

data class BoolLiteral(
    val value: Boolean,
) : Node(), LiteralExpression

data class IntLiteral(
    val value: Int,
    val radix: Int = 10,
) : Node(), NumberLiteral

data class LongLiteral(
    val value: Long,
    val radix: Int = 10,
) : Node(), NumberLiteral

data class FloatLiteral(
    val value: Float,
) : Node(), NumberLiteral

data class DoubleLiteral(
    val value: Double,
) : Node(), NumberLiteral

data class StringLiteral(
    val value: String,
) : Node(), LiteralExpression

class NullLiteral : Node(), LiteralExpression

data class ArrayLiteral(
    val elements: List<Expression> = emptyList(),
) : Node(), LiteralExpression

data class MapLiteral(
    val entries: List<MapEntry> = emptyList(),
) : Node(), LiteralExpression

data class MapEntry(
    val key: Expression,
    val value: Expression,
) : Node()
