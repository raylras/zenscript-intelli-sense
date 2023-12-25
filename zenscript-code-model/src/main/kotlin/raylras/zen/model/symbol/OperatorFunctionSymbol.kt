package raylras.zen.model.symbol

import raylras.zen.model.type.FunctionType

interface OperatorFunctionSymbol : Symbol, Executable {
    val operator: Operator

    override val type: FunctionType
}
