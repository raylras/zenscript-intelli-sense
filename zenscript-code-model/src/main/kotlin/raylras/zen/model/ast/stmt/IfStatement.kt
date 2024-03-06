package raylras.zen.model.ast.stmt

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Statement

data class IfStatement(
    val condition: Expression,
    val thenPart: Statement,
    val elsePart: Statement? = null
) : Node(), Statement
