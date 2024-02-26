package raylras.zen.model.ast.stmt

import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Statement

data class InvalidStatement(
    val text: String,
) : Statement, Node()
