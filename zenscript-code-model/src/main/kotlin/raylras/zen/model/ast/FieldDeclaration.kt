package raylras.zen.model.ast

import com.strumenta.kolasu.model.EntityDeclaration
import com.strumenta.kolasu.model.Expression
import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node

data class FieldDeclaration(
    val simpleName: String,
    override val typeAnnotation: TypeLiteral? = null,
    val initializer: Expression? = null,
) : Node(), EntityDeclaration, Named, PossiblyAnnotatedType {
    override val name: String
        get() = simpleName
}
