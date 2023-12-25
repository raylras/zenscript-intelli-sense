package raylras.zen.util

fun TextRange.toLspRange(): org.eclipse.lsp4j.Range {
    return org.eclipse.lsp4j.Range(start.toLspPosition(), end.toLspPosition())
}

fun org.eclipse.lsp4j.Range.toTextRange(): TextRange {
    return TextRange(start.toTextPosition(), end.toTextPosition())
}
