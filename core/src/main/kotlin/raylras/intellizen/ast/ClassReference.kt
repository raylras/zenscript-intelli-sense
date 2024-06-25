package raylras.intellizen.ast

import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node

data class ClassReference(
    val identifier: Identifier
) : Node(), Named {
    override val name: String
        get() = identifier.text
}
