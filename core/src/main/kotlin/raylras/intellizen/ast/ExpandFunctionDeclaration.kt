package raylras.intellizen.ast

import com.strumenta.kolasu.model.EntityDeclaration
import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Statement

data class ExpandFunctionDeclaration(
    val receiverTypeLiteral: TypeLiteral,
    val identifier: Identifier,
    val parameters: List<ParameterDeclaration> = emptyList(),
    val returnTypeLiteral: TypeLiteral? = null,
    val body: List<Statement> = emptyList(),
) : Node(), EntityDeclaration, Named {
    override val name: String
        get() = identifier.text
}
