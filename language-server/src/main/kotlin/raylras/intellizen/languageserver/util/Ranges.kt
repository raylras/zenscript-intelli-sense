package raylras.intellizen.languageserver.util

import raylras.intellizen.util.TextRange

fun TextRange.toLspRange(): org.eclipse.lsp4j.Range {
    return org.eclipse.lsp4j.Range(start.toLspPosition(), end.toLspPosition())
}

fun org.eclipse.lsp4j.Range.toTextRange(): TextRange {
    return TextRange(start.toTextPosition(), end.toTextPosition())
}
