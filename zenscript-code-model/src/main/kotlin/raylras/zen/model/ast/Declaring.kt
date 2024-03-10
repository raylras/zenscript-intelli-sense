package raylras.zen.model.ast

interface DeclaringDescription {
    val declaringType: DeclaringType
}

enum class DeclaringType {
    VAR,
    VAL,
    STATIC,
    GLOBAL,
    NONE
}
