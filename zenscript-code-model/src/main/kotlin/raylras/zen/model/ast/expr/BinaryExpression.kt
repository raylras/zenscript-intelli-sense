package raylras.zen.model.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node

data class BinaryExpression(
    val left: Expression,
    val operator: BinaryOperator,
    val right: Expression,
) : Node(), Expression
