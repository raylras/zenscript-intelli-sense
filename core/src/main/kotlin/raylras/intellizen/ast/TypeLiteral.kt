package raylras.intellizen.ast

import com.strumenta.kolasu.model.Node

sealed interface TypeLiteral {
    fun asString(): String
}

data class ArrayTypeLiteral(
    val baseType: TypeLiteral
) : Node(), TypeLiteral {
    override fun asString() = baseType.asString() + "[]"
}

data class ListTypeLiteral(
    val baseType: TypeLiteral
) : Node(), TypeLiteral {
    override fun asString() = "[" + baseType.asString() + "]"
}

data class MapTypeLiteral(
    val keyType: TypeLiteral,
    val valueType: TypeLiteral,
) : Node(), TypeLiteral {
    override fun asString() = valueType.asString() + "[" + keyType.asString() + "]"
}

data class FunctionTypeLiteral(
    val parameterTypes: List<TypeLiteral>,
    val returnType: TypeLiteral,
) : Node(), TypeLiteral {
    override fun asString() = parameterTypes.joinToString(
        separator = ",",
        prefix = "function(",
        postfix = ")${returnType.asString()}"
    ) { paramType -> paramType.asString() }
}

data class ReferenceTypeLiteral(
    val typeName: String,
) : Node(), TypeLiteral {
    override fun asString() = typeName
}

data class UnionTypeLiteral(
    val subTypes: List<TypeLiteral>
) : Node(), TypeLiteral {
    override fun asString() = subTypes.joinToString(" | ")
}

data class IntersectionTypeLiteral(
    val subTypes: List<TypeLiteral>
) : Node(), TypeLiteral {
    override fun asString() = subTypes.joinToString(" & ")
}

data class PrimitiveTypeLiteral(
    val typeName: String,
) : Node(), TypeLiteral {
    override fun asString() = typeName
}
