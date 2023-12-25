package raylras.zen.model.symbol

import raylras.zen.model.type.Type

interface Symbol {
    val simpleName: String

    val type: Type
}
