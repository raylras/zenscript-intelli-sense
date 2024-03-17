package raylras.zen.model.ast

import com.strumenta.kolasu.model.Derived
import com.strumenta.kolasu.model.EntityDeclaration
import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node

data class ClassDeclaration(
    val simpleName: Name,
    val interfaces: List<ClassReference> = emptyList(),
    val classBodyEntities: List<Node>
) : Node(), EntityDeclaration, Named by simpleName {
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
}

fun ClassDeclaration.walkInterfaces(): Sequence<ClassDeclaration> {
    val deque = ArrayDeque(interfaces)
    return generateSequence {
        deque.removeFirstOrNull()?.let { popped ->
            val referred = requireNotNull(popped.ref.referred) { "Unsolved interface ${popped.name}" }
            deque.addAll(referred.interfaces)
            referred
        }
    }
}
