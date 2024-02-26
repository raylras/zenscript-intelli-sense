package raylras.zen.model.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node

data class IntRangeExpression(
    val from: Expression,
    val to: Expression
) : Expression, Node()
