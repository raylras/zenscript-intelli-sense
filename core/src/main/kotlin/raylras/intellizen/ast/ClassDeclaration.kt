package raylras.intellizen.ast

import com.strumenta.kolasu.model.Derived
import com.strumenta.kolasu.model.EntityDeclaration
import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node

data class ClassDeclaration(
    val identifier: Identifier,
    val interfaces: List<ClassReference> = emptyList(),
    val classBodyEntities: List<Node>
) : Node(), EntityDeclaration, Named {
    @Derived
    val declaredFields: List<FieldDeclaration>
        get() = classBodyEntities.filterIsInstance<FieldDeclaration>()

    @Derived
    val declaredConstructors: List<ConstructorDeclaration>
        get() = classBodyEntities.filterIsInstance<ConstructorDeclaration>()

    @Derived
    val declaredMethods: List<FunctionDeclaration>
        get() = classBodyEntities.filterIsInstance<FunctionDeclaration>()

    override val name: String
        get() = identifier.text
}
