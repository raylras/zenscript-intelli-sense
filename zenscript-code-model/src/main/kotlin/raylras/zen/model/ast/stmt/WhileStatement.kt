package raylras.zen.model.ast.stmt

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Statement

data class WhileStatement(
    val condition: Expression,
    val body: Statement
) : Node(), Statement
