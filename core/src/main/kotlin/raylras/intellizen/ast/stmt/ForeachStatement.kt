package raylras.intellizen.ast.stmt

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Statement
import raylras.intellizen.ast.VariableDeclaration

data class ForeachStatement(
    val variables: List<VariableDeclaration>,
    val iterable: Expression,
    val body: List<Statement>,
) : Node(), Statement
