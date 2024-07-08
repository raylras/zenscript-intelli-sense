package raylras.intellizen.symbol

interface ParameterSymbol : Symbol, Modifiable {
    val isOptional: Boolean

    val isVararg: Boolean
}
