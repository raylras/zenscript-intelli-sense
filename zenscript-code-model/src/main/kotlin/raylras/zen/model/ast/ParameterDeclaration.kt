package raylras.zen.model.ast

import com.strumenta.kolasu.model.EntityDeclaration
import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node

data class ParameterDeclaration(
    override val declaringKind: DeclaringKind = DeclaringKind.NONE,
    val isVararg: Boolean = false,
    val simpleName: Name,
    val typeLiteral: TypeLiteral?,
    val defaultValue: Expression? = null,
) : Node(), EntityDeclaration, DeclaringDescription, Named by simpleName
