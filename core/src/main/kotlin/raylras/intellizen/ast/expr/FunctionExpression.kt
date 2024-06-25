package raylras.intellizen.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Statement
import raylras.intellizen.ast.ParameterDeclaration
import raylras.intellizen.ast.TypeLiteral

data class FunctionExpression(
    val parameters: List<ParameterDeclaration> = emptyList(),
    val returnTypeLiteral: TypeLiteral? = null,
    val body: List<Statement> = emptyList(),
) : Node(), Expression
