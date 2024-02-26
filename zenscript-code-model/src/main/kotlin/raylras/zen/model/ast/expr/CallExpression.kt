package raylras.zen.model.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node

data class CallExpression(
    val receiver: Expression,
    val arguments: List<Expression>,
) : Expression, Node()
