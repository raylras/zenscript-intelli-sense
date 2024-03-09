package raylras.zen.model.ast

import org.antlr.v4.runtime.Token
import raylras.zen.model.parser.ZenScriptLexer

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

fun Token?.asDeclaringType(): DeclaringType = when (this) {
    null -> DeclaringType.NONE
    else -> when (this.type) {
        ZenScriptLexer.VAR -> DeclaringType.VAR
        ZenScriptLexer.VAL -> DeclaringType.VAL
        ZenScriptLexer.STATIC -> DeclaringType.STATIC
        ZenScriptLexer.GLOBAL -> DeclaringType.GLOBAL
        else -> throw NoSuchElementException(
            "Unknown declaring type for token: $this." +
                    " Allowed values: ${DeclaringType.entries.joinToString()}"
        )
    }
}
