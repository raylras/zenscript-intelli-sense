package raylras.zen.model.type

import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.SemanticEntity
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.symbol.hasCasterFor

interface Type: SemanticEntity {
    val typeName: String
    val simpleTypeName: String
        get() = typeName

    fun isSupertypeTo(type: Type): Boolean {
        return this.javaClass.isAssignableFrom(type.javaClass)
    }

    fun isCastableTo(that: Type?, env: CompilationEnvironment): Boolean {
        if (that == null) return false
        return that.isSupertypeTo(this) || this.hasCasterFor(that, env)
    }

    fun getExpands(env: CompilationEnvironment?): Sequence<Symbol> {
        return env?.expandFunctions?.filter { it.expandingType.isSupertypeTo(this) } ?: emptySequence()
    }

    fun isNullable(): Boolean {
        return when (this) {
            is NumberType, BoolType, VoidType, ErrorType -> false
            else -> true
        }
    }
}

fun Type?.test(target: Type?, env: CompilationEnvironment): CastingKind {
    return when {
        this == null || target == null -> CastingKind.MISMATCH

        this == target -> {
            CastingKind.SELF
        }

        target.isSupertypeTo(this) -> {
            CastingKind.INHERIT
        }

        this.isCastableTo(target, env) -> {
            CastingKind.CASTER
        }

        else -> CastingKind.MISMATCH
    }
}
