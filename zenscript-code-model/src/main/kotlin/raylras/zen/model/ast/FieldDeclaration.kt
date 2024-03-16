package raylras.zen.model.ast

import com.strumenta.kolasu.model.EntityDeclaration
import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node

data class FieldDeclaration(
    override val declaringKind: DeclaringKind,
    val simpleName: Name,
    val typeLiteral: TypeLiteral? = null,
    val initializer: Expression? = null,
) : Node(), EntityDeclaration, DeclaringDescription, Named by simpleName
