package raylras.zen.model.ast

import com.strumenta.kolasu.model.*

data class ClassDeclaration(
    val simpleName: String,
    val interfaces: List<ReferenceByName<ClassDeclaration>> = emptyList(),
    val classBodyEntities: List<Node>
) : Node(), EntityDeclaration, Named {
    override val name: String
        get() = simpleName

    @Derived
    val declaredFields: List<FieldDeclaration>
        get() = classBodyEntities.filterIsInstance<FieldDeclaration>()

    @Derived
    val declaredConstructors: List<ConstructorDeclaration>
        get() = classBodyEntities.filterIsInstance<ConstructorDeclaration>()

    @Derived
    val declaredMethods: List<FunctionDeclaration>
        get() = classBodyEntities.filterIsInstance<FunctionDeclaration>()

    @Derived
    val fields: Sequence<FieldDeclaration>
        get() = declaredFields.asSequence() + walkInterfaces().flatMap { it.declaredFields }

    @Derived
    val methods: Sequence<FunctionDeclaration>
        get() = declaredMethods.asSequence() + walkInterfaces().flatMap { it.declaredMethods }

    fun walkInterfaces(): Sequence<ClassDeclaration> {
        val stack = ArrayDeque(interfaces)
        return generateSequence {
            stack.removeFirstOrNull()?.let { popped ->
                val referred = requireNotNull(popped.referred) { "Unsolved interface ${popped.name}" }
                stack.addAll(referred.interfaces)
                referred
            }
        }
    }
}
