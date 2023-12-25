package raylras.zen.model.symbol

interface ParameterSymbol : Symbol {
    val isOptional: Boolean

    val isVararg: Boolean
}
