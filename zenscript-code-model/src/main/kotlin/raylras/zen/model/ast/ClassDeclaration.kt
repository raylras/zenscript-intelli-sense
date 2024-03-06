package raylras.zen.model.ast

import com.strumenta.kolasu.model.EntityDeclaration
import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.ReferenceByName

data class ClassDeclaration(
    val simpleName: String,
    val declaredFields: List<FieldDeclaration> = emptyList(),
    val declaredConstructors: List<ConstructorDeclaration> = emptyList(),
    val declaredMethods: List<FunctionDeclaration> = emptyList(),
    val interfaces: List<ReferenceByName<ClassDeclaration>> = emptyList()
) : Node(), EntityDeclaration, Named {
    override val name: String
        get() = simpleName
}
