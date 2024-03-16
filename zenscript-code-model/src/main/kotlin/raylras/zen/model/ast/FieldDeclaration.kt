package raylras.zen.model.ast

import com.strumenta.kolasu.model.EntityDeclaration
import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node

data class FieldDeclaration(
    override val declaringType: DeclaringType,
    val simpleName: String,
    val typeLiteral: TypeLiteral? = null,
    val initializer: Expression? = null,
) : Node(), EntityDeclaration, Named, DeclaringDescription {
    override val name: String
        get() = simpleName
}
