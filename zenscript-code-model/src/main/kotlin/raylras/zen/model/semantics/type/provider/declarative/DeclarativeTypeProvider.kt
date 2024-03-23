package raylras.zen.model.semantics.type.provider.declarative

import com.strumenta.kolasu.model.Node
import raylras.zen.model.semantics.type.Type
import raylras.zen.model.semantics.type.provider.TypeProvider
import kotlin.reflect.KClass
import kotlin.reflect.safeCast

fun <N : Node> typeFor(
    nodeClass: KClass<N>,
    rule: TypeProvider.(N) -> Type?
): DeclarativeTypeRule<N> {
    return DeclarativeTypeRule(nodeClass) { listOfNotNull(rule(it)) }
}

fun <N : Node> typesFor(
    nodeClass: KClass<N>,
    rule: TypeProvider.(N) -> Iterable<Type?>?
): DeclarativeTypeRule<N> {
    return DeclarativeTypeRule(nodeClass) { rule(it)?.filterNotNull().orEmpty() }
}

open class DeclarativeTypeProvider(
    vararg rules: DeclarativeTypeRule<out Node>
) : TypeProvider {
    private val rules = rules.asList()

    override fun typesOf(node: Any?): Iterable<Type> {
        return when (node) {
            is Node -> rules.find { it.isCompatibleWith(node) }?.invoke(this, node) ?: emptyList()
            else -> emptyList()
        }
    }
}

class DeclarativeTypeRule<N : Node>(
    private val nodeClass: KClass<N>,
    private val rule: TypeProvider.(N) -> Iterable<Type>
) : (TypeProvider, Node) -> Iterable<Type> {
    fun isCompatibleWith(node: Node): Boolean {
        return nodeClass.isInstance(node)
    }

    override fun invoke(typeProvider: TypeProvider, node: Node): Iterable<Type> {
        return nodeClass.safeCast(node)?.let {
            rule.invoke(typeProvider, it)
        } ?: emptyList()
    }
}
