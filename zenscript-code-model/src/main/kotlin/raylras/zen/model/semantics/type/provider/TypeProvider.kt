package raylras.zen.model.semantics.type.provider

import raylras.zen.model.semantics.type.Type

interface TypeProvider {
    fun typesOf(node: Any?): Iterable<Type>
}

fun TypeProvider.typeOf(node: Any?): Type? {
    return typesOf(node).firstOrNull()
}

inline fun <reified T: Type> TypeProvider.instanceTypeOf(node: Any?): T? {
    return typesOf(node).filterIsInstance<T>().firstOrNull()
}
