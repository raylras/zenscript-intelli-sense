package raylras.intellizen.type

import raylras.intellizen.CompilationEnvironment

object ErrorType : Type {
    override val typeName: String = "ErrorType"

    override fun isSupertypeTo(type: Type) = false

    override fun isCastableTo(that: Type?, env: CompilationEnvironment) = false

    override fun toString(): String = typeName
}
