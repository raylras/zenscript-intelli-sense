package raylras.zen.model.type

data class FunctionType(val returnType: Type, val parameterTypes: List<Type>) : Type {
    constructor(returnType: Type, vararg parameterTypes: Type) : this(
        returnType,
        parameterTypes.asList()
    )

    override val typeName by lazy {
        "function" + parameterTypes.joinToString(",", "(", ")${returnType.typeName}") { it.typeName }
    }

    override val simpleTypeName by lazy {
        "function" + parameterTypes.joinToString(",", "(", ")${returnType.simpleTypeName}") { it.simpleTypeName }
    }

    override fun toString() = typeName
}
