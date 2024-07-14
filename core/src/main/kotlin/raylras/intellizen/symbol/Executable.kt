package raylras.intellizen.symbol

import raylras.intellizen.type.Type

interface Executable {
    val parameters: List<ParameterSymbol>

    val returnType: Type
}
