package raylras.zen.model.ast

import com.strumenta.kolasu.model.Node

sealed class TypeLiteral : Node() {
    abstract val text: String
}

data class ArrayTypeLiteral(
    val baseType: TypeLiteral
) : TypeLiteral() {
    override val text: String
        get() = baseType.text + "[]"
}

data class ListTypeLiteral(
    val baseType: TypeLiteral
) : TypeLiteral() {
    override val text: String
        get() = "[" + baseType.text + "]"
}

data class MapTypeLiteral(
    val keyType: TypeLiteral,
    val valueType: TypeLiteral,
) : TypeLiteral() {
    override val text: String
        get() = valueType.text + "[" + keyType.text + "]"
}

data class FunctionTypeLiteral(
    val parameterTypes: List<TypeLiteral>,
    val returnType: TypeLiteral,
) : TypeLiteral() {
    override val text: String
        get() = "function${parameterTypes.joinToString(",", prefix = "(", postfix = ")${returnType.text}") { it.text }}"
}

data class ReferenceTypeLiteral(
    val qualifiedName: String,
) : TypeLiteral() {
    override val text: String
        get() = qualifiedName
}

data class UnionTypeLiteral(
    val subTypes: List<TypeLiteral>
) : TypeLiteral() {
    override val text: String
        get() = subTypes.joinToString(" | ")
}

data class IntersectionTypeLiteral(
    val subTypes: List<TypeLiteral>
) : TypeLiteral() {
    override val text: String
        get() = subTypes.joinToString(" & ")
}

data class PrimitiveTypeLiteral(
    val simpleName: String,
) : TypeLiteral() {
    override val text: String
        get() = simpleName
}
