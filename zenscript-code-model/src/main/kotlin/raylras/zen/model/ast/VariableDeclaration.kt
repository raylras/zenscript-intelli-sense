package raylras.zen.model.ast

import com.strumenta.kolasu.model.*

data class VariableDeclaration(
    val simpleName: String,
    override val typeAnnotation: TypeLiteral? = null,
    val initializer: Expression? = null,
) : EntityDeclaration, Statement, Named, PossiblyAnnotatedType, Node() {
    override val name: String
        get() = simpleName
}
