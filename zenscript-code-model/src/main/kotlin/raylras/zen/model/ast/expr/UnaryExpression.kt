package raylras.zen.model.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node

data class UnaryExpression(
    val operator: UnaryOperator,
    val expression: Expression,
) : Node(), Expression
