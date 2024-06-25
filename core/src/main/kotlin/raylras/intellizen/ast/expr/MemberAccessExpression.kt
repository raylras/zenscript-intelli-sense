package raylras.intellizen.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node
import raylras.intellizen.ast.Identifier

data class MemberAccessExpression(
    val receiver: Expression,
    val member: Identifier,
) : Node(), Expression
