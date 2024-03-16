package raylras.zen.model.ast.expr

import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Statement
import raylras.zen.model.ast.ParameterDeclaration
import raylras.zen.model.ast.TypeLiteral

data class FunctionExpression(
    val parameters: List<ParameterDeclaration> = emptyList(),
    val returnTypeLiteral: TypeLiteral? = null,
    val body: List<Statement> = emptyList(),
) : Node(), Expression
