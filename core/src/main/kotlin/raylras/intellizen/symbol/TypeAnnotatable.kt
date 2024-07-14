package raylras.intellizen.symbol

import raylras.intellizen.parser.ZenScriptParser.TypeLiteralContext
import raylras.intellizen.util.TextPosition

interface TypeAnnotatable: ParseTreeLocatable {
    val typeAnnotationCst: TypeLiteralContext?
    val typeAnnotationTextPosition: TextPosition
}