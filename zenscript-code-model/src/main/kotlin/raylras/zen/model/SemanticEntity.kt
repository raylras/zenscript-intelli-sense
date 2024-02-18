package raylras.zen.model

import raylras.zen.model.symbol.Symbol
import raylras.zen.model.type.Type

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
