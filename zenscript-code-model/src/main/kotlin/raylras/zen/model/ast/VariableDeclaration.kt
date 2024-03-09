package raylras.zen.model.ast

import com.strumenta.kolasu.model.*

data class VariableDeclaration(
    override val declaringType: DeclaringType,
    val simpleName: String,
    override val typeAnnotation: TypeLiteral? = null,
    val initializer: Expression? = null,
) : Node(), EntityDeclaration, Statement, Named, DeclaringDescription, PossiblyAnnotatedType {
    override val name: String
        get() = simpleName
}
