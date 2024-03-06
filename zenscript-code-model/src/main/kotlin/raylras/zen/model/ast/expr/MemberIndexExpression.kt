package raylras.zen.model.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node

data class MemberIndexExpression(
    val receiver: Expression,
    val index: Expression,
) : Node(), Expression
