package raylras.intellizen.ast

import com.strumenta.kolasu.model.EntityDeclaration
import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node

data class FieldDeclaration(
    override val declaringKind: DeclaringKind,
    val identifier: Identifier,
    val typeLiteral: TypeLiteral? = null,
    val initializer: Expression? = null,
) : Node(), EntityDeclaration, DeclaringDescription, Named {
    override val name: String
        get() = identifier.text
}
