package raylras.intellizen.languageserver.util

import raylras.intellizen.util.TextPosition

fun TextPosition.toLspPosition(): org.eclipse.lsp4j.Position {
    return org.eclipse.lsp4j.Position(line, column)
}

fun org.eclipse.lsp4j.Position.toTextPosition(): TextPosition {
    return TextPosition(line, character)
}
