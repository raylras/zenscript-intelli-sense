package raylras.zen.model.symbol

import raylras.zen.model.parser.ZenScriptParser.TypeLiteralContext
import raylras.zen.util.TextPosition

interface TypeAnnotatable: ParseTreeLocatable {
    val typeAnnotationCst: TypeLiteralContext?
    val typeAnnotationTextPosition: TextPosition
}