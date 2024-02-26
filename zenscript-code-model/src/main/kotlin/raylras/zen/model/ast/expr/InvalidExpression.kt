package raylras.zen.model.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node

data class InvalidExpression(
    val expr: String,
    val exception: Throwable? = null,
) : Expression, Node()
