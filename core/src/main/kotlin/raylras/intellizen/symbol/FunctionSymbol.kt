package raylras.intellizen.symbol

import raylras.intellizen.type.FunctionType

interface FunctionSymbol : Symbol, Executable, Modifiable {
    override val type: FunctionType
}
