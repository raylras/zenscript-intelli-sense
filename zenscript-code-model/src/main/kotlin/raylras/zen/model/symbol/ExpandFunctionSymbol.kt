package raylras.zen.model.symbol

import raylras.zen.model.type.Type

interface ExpandFunctionSymbol : Symbol, Executable {
    val expandingType: Type
}
