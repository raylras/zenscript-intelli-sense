package raylras.zen.model.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node

data class BracketHandlerExpression(
    val content: String
) : Expression, Node()
