package raylras.zen.model.type

import raylras.zen.model.CompilationEnvironment

data class FunctionType(val returnType: Type, val parameterTypes: List<Type>) : Type {
    override val typeName by lazy {
        "function" + parameterTypes.joinToString(",", "(", ")${returnType.typeName}") { it.typeName }
    }

    override val simpleTypeName by lazy {
        "function" + parameterTypes.joinToString(",", "(", ")${returnType.simpleTypeName}") { it.simpleTypeName }
    }

    override fun isCastableTo(that: Type?, env: CompilationEnvironment?): Boolean {
        return when {
            that is FunctionType -> {
                this.isCastableToFn(that, env)
            }

            that is ClassType && that.isFunctionalInterface() -> {
                this.isCastableToFn(that.firstAnonymousFunctionOrNull()?.type, env)
            }

            else -> false
        }
    }

    override fun toString() = typeName
}

private fun FunctionType.isCastableToFn(that: FunctionType?, env: CompilationEnvironment?): Boolean {
    return that != null && (this.parameterTypes zip that.parameterTypes).all { (pThis, pThat) ->
        pThis.isCastableTo(pThat, env)
    }
}
