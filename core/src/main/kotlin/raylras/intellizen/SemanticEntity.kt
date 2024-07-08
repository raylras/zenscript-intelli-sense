package raylras.intellizen

import raylras.intellizen.symbol.Symbol
import raylras.intellizen.type.Type

interface SemanticEntity

fun Sequence<SemanticEntity>.mapToType(): Sequence<Type> {
    return mapNotNull {
        when (it) {
            is Type -> it
            is Symbol -> it.type
            else -> null
        }
    }
}

fun Sequence<SemanticEntity>.mapToSymbol(): Sequence<Symbol> {
    return mapNotNull { it as? Symbol }
}
