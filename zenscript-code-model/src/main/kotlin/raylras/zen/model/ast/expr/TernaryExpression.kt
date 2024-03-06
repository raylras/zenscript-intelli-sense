package raylras.zen.model.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node

data class TernaryExpression(
    val condition: Expression,
    val truePart: Expression,
    val falsePart: Expression,
) : Node(), Expression
