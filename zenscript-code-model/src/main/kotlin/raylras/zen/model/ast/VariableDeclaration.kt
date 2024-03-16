package raylras.zen.model.ast

import com.strumenta.kolasu.model.*

data class VariableDeclaration(
    override val declaringKind: DeclaringKind,
    val simpleName: Name,
    val typeLiteral: TypeLiteral? = null,
    val initializer: Expression? = null,
) : Node(), EntityDeclaration, Statement, DeclaringDescription, Named by simpleName
