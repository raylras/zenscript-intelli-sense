package raylras.zen.model.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node

data class MemberAccessExpression(
    val receiver: Expression,
    val memberName: String,
) : Expression, Node()
