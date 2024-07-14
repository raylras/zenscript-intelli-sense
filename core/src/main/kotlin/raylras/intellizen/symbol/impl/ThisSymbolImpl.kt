package raylras.intellizen.symbol.impl

import raylras.intellizen.symbol.ThisSymbol
import raylras.intellizen.type.Type

fun createThisSymbol(typeSupplier: () -> Type): ThisSymbol {
    return object : ThisSymbol {
        override val simpleName: String = "this"

        override val type: Type by lazy { typeSupplier() }

        override fun toString(): String = simpleName
    }
}
