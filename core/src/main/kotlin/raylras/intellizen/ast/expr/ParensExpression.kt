package raylras.intellizen.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node

data class ParensExpression(
    val expression: Expression,
) : Node(), Expression
