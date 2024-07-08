package raylras.intellizen.symbol

import raylras.intellizen.SemanticEntity
import raylras.intellizen.type.Type

interface Symbol: SemanticEntity {
    val simpleName: String

    val type: Type
}
