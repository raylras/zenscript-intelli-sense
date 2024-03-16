package raylras.zen.model.ast

import com.strumenta.kolasu.model.EntityDeclaration
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.PossiblyNamed
import com.strumenta.kolasu.model.Statement

data class FunctionDeclaration(
    override val declaringKind: DeclaringKind,
    val simpleName: Name?,
    val parameters: List<ParameterDeclaration> = emptyList(),
    val returnTypeLiteral: TypeLiteral? = null,
    val body: List<Statement> = emptyList(),
) : Node(), EntityDeclaration, DeclaringDescription, PossiblyNamed {
    override val name: String?
        get() = simpleName?.name
}
