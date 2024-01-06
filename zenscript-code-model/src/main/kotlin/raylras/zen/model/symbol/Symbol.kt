package raylras.zen.model.symbol

import raylras.zen.model.SemanticEntity
import raylras.zen.model.type.Type

interface Symbol: SemanticEntity {
    val simpleName: String

    val type: Type
}
