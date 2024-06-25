package raylras.intellizen.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node

data class ArrayAccessExpression(
    val array: Expression,
    val index: Expression,
) : Node(), Expression
