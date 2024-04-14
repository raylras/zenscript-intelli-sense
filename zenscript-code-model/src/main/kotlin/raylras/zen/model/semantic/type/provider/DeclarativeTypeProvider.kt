package raylras.zen.model.semantic.type.provider

import com.strumenta.kolasu.model.Node
import raylras.zen.model.semantic.type.Type
import kotlin.reflect.KClass
import kotlin.reflect.safeCast

inline fun <reified N : Node> DeclarativeTypeProvider.typeFor(
    crossinline rule: N.() -> Type?
) {
    typeRules += DeclarativeTypeRule(N::class) { listOfNotNull(rule(this)) }
}

inline fun <reified N : Node> DeclarativeTypeProvider.multipleTypeFor(
    crossinline rule: N.() -> Iterable<Type?>?
) {
    typeRules += DeclarativeTypeRule(N::class) { rule(this)?.filterNotNull().orEmpty() }
}

open class DeclarativeTypeProvider(
    val typeRules: MutableList<DeclarativeTypeRule<out Node>> = mutableListOf()
) : TypeProvider {
    override fun multipleTypeOf(node: Any?): Iterable<Type> {
        return typeRules.find { it.isCompatibleWith(node) }?.invoke(node) ?: emptyList()
    }
}

class DeclarativeTypeRule<N : Node>(
    private val nodeClass: KClass<N>,
    private val rule: N.() -> Iterable<Type>
) : (Any?) -> Iterable<Type> {
    fun isCompatibleWith(node: Any?): Boolean {
        return nodeClass.isInstance(node)
    }

    override fun invoke(node: Any?): Iterable<Type> {
        return nodeClass.safeCast(node)?.let { rule(it) } ?: emptyList()
    }
}
