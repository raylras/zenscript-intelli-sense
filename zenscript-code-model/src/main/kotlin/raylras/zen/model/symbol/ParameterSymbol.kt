package raylras.zen.model.symbol

interface ParameterSymbol : Symbol, Modifiable {
    val isOptional: Boolean

    val isVararg: Boolean
}
