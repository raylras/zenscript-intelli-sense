package raylras.zen.model.ast

import com.strumenta.kolasu.model.*

data class ClassDeclaration(
    val simpleName: String,
    val members: List<EntityDeclaration> = emptyList(),
    val interfaces: List<ReferenceByName<ClassDeclaration>> = emptyList()
) : EntityDeclaration, Named, Node() {
    override val name: String
        get() = simpleName

    @Derived
    val constructors: List<ConstructorDeclaration>
        get() = members.filterIsInstance<ConstructorDeclaration>()

    @Derived
    val methods: List<FunctionDeclaration>
        get() = members.filterIsInstance<FunctionDeclaration>()

    @Derived
    val fields: List<FieldDeclaration>
        get() = members.filterIsInstance<FieldDeclaration>()
}
