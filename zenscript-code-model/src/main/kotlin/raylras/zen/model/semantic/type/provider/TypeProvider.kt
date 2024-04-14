package raylras.zen.model.semantic.type.provider

import raylras.zen.model.semantic.type.Type

interface TypeProvider {
    fun multipleTypeOf(node: Any?): Iterable<Type>
}

fun TypeProvider.typeOf(node: Any?): Type? {
    return multipleTypeOf(node).firstOrNull()
}

inline fun <reified T : Type> TypeProvider.reifiedMultipleTypeOf(node: Any?): Iterable<T> {
    return multipleTypeOf(node).filterIsInstance<T>()
}

inline fun <reified T : Type> TypeProvider.reifiedTypeOf(node: Any?): T? {
    return multipleTypeOf(node).filterIsInstance<T>().firstOrNull()
}
