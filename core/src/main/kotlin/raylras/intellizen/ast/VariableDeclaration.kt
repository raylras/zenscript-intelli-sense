package raylras.intellizen.ast

import com.strumenta.kolasu.model.*

data class VariableDeclaration(
    override val declaringKind: DeclaringKind,
    val identifier: Identifier,
    val typeLiteral: TypeLiteral? = null,
    val initializer: Expression? = null,
) : Node(), EntityDeclaration, Statement, DeclaringDescription, Named {
    override val name: String
        get() = identifier.text
}
