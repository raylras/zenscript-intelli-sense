package raylras.intellizen.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node
import raylras.intellizen.ast.TypeLiteral

data class CastExpression(
    val expression: Expression,
    val typeLiteral: TypeLiteral,
) : Node(), Expression
