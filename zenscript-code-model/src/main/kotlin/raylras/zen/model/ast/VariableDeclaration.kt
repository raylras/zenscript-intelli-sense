package raylras.zen.model.ast

import com.strumenta.kolasu.model.*

data class VariableDeclaration(
    override val declaringKind: DeclaringKind,
    val simpleName: String,
    val typeLiteral: TypeLiteral? = null,
    val initializer: Expression? = null,
) : Node(), EntityDeclaration, Statement, Named, DeclaringDescription {
    override val name: String
        get() = simpleName
}
