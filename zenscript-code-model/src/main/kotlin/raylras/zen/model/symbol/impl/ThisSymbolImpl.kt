package raylras.zen.model.symbol.impl

import raylras.zen.model.symbol.ThisSymbol
import raylras.zen.model.type.Type

fun createThisSymbol(typeSupplier: () -> Type): ThisSymbol {
    return object : ThisSymbol {
        override val simpleName: String = "this"

        override val type: Type by lazy { typeSupplier() }

        override fun toString(): String = simpleName
    }
}
