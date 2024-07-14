package raylras.intellizen.symbol

import raylras.intellizen.type.Type

interface ExpandFunctionSymbol : Symbol, Executable {
    val expandingType: Type
}
