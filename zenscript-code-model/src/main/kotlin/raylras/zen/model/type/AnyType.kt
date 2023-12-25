package raylras.zen.model.type

import raylras.zen.model.CompilationEnvironment

object AnyType : Type {
    override val typeName = "any"

    override fun isSupertypeTo(type: Type) = true

    override fun isCastableTo(that: Type?, env: CompilationEnvironment) = true

    override fun toString() = typeName
}
