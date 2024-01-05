package raylras.zen.model.symbol.impl

import raylras.zen.model.symbol.ThisSymbol
import raylras.zen.model.type.Type

fun createThisSymbol(typeSupplier: () -> Type): ThisSymbol {
    return object : ThisSymbol {
        override val simpleName: String
            get() = "this"

        override val type: Type
            get() = typeSupplier()

        override fun toString(): String {
            return simpleName
        }
    }
}
