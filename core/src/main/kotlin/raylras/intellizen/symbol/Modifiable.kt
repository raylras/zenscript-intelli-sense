package raylras.intellizen.symbol

interface Modifiable {
    val modifier: Modifier

    val isStatic: Boolean
        get() = when (modifier) {
            Modifier.STATIC, Modifier.IMPLICIT_STATIC, Modifier.GLOBAL -> true
            else -> false
        }

    val isGlobal: Boolean
        get() = when (modifier) {
            Modifier.GLOBAL -> true
            else -> false
        }

    val isReadonly: Boolean
        get() = when (modifier) {
            Modifier.VAL, Modifier.IMPLICIT_VAL, Modifier.STATIC, Modifier.GLOBAL -> true
            else -> false
        }

    enum class Modifier {
        VAR, VAL, STATIC, GLOBAL,
        IMPLICIT_VAR, IMPLICIT_VAL, IMPLICIT_STATIC,
        ERROR
    }
}

val Symbol.isStatic: Boolean
    get() = when (this) {
        is Modifiable -> isStatic
        else -> false
    }

val Symbol.isGlobal: Boolean
    get() = when (this) {
        is Modifiable -> isGlobal
        else -> false
    }

val Symbol.isReadonly: Boolean
    get() = when (this) {
        is Modifiable -> isReadonly
        else -> false
    }
