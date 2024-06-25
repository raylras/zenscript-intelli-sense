package raylras.intellizen.ast.stmt

import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Statement

data class BlockStatement(
    val statements: List<Statement> = emptyList(),
) : Node(), Statement
