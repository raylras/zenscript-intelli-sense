package raylras.intellizen.ast

interface DeclaringDescription {
    val declaringKind: DeclaringKind
}

enum class DeclaringKind {
    VAR,
    VAL,
    STATIC,
    GLOBAL,
    NONE
}
