package raylras.zen.model.symbol

import raylras.zen.model.type.FunctionType

interface FunctionSymbol : Symbol, Executable, Modifiable {
    override val type: FunctionType
}
