package raylras.zen.model.symbol

import raylras.zen.model.type.Type

interface Executable {
    val parameters: List<ParameterSymbol>

    val returnType: Type
}
