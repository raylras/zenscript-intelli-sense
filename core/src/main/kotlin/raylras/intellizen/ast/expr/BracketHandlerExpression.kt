package raylras.intellizen.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node

data class BracketHandlerExpression(
    val content: String
) : Node(), Expression
