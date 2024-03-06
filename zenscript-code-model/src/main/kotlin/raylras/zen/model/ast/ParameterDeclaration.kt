package raylras.zen.model.ast

import com.strumenta.kolasu.model.EntityDeclaration
import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node

data class ParameterDeclaration(
    val simpleName: String,
    val defaultValue: Expression? = null,
) : Node(), EntityDeclaration, Named {
    override val name: String
        get() = simpleName
}
