package raylras.intellizen.symbol

import raylras.intellizen.type.FunctionType

interface OperatorFunctionSymbol : Symbol, Executable {
    val operator: Operator

    override val type: FunctionType
}
