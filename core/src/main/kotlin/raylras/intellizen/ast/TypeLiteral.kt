package raylras.intellizen.ast

import com.strumenta.kolasu.model.Node

sealed class TypeLiteral : Node() {
    abstract fun asString(): String
}

data class ArrayTypeLiteral(
    val baseType: TypeLiteral
) : TypeLiteral() {
    override fun asString() = baseType.asString() + "[]"
}

data class ListTypeLiteral(
    val baseType: TypeLiteral
) : TypeLiteral() {
    override fun asString() = "[" + baseType.asString() + "]"
}

data class MapTypeLiteral(
    val keyType: TypeLiteral,
    val valueType: TypeLiteral,
) : TypeLiteral() {
    override fun asString() = valueType.asString() + "[" + keyType.asString() + "]"
}

data class FunctionTypeLiteral(
    val parameterTypes: List<TypeLiteral>,
    val returnType: TypeLiteral,
) : TypeLiteral() {
    override fun asString() = parameterTypes.joinToString(
        separator = ",",
        prefix = "function(",
        postfix = ")${returnType.asString()}"
    ) { paramType -> paramType.asString() }
}

data class ReferenceTypeLiteral(
    val typeName: String,
) : TypeLiteral() {
    override fun asString() = typeName
}

data class UnionTypeLiteral(
    val subTypes: List<TypeLiteral>
) : TypeLiteral() {
    override fun asString() = subTypes.joinToString(" | ")
}

data class IntersectionTypeLiteral(
    val subTypes: List<TypeLiteral>
) : TypeLiteral() {
    override fun asString() = subTypes.joinToString(" & ")
}

data class PrimitiveTypeLiteral(
    val typeName: String,
) : TypeLiteral() {
    override fun asString() = typeName
}
