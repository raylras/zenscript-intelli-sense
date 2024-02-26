package raylras.zen.model.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node
import raylras.zen.model.ast.TypeLiteral

data class CastExpression(
    val expression: Expression,
    val typeLiteral: TypeLiteral,
) : Expression, Node()
