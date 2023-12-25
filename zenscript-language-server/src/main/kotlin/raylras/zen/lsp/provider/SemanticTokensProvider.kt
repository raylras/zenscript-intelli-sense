package raylras.zen.lsp.provider

import org.eclipse.lsp4j.SemanticTokens
import org.eclipse.lsp4j.SemanticTokensParams
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Listener
import raylras.zen.util.BASE_COLUMN
import raylras.zen.util.BASE_LINE
import raylras.zen.util.TextRange

object SemanticTokensProvider {
    fun semanticTokensFull(unit: CompilationUnit?, params: SemanticTokensParams): SemanticTokens? {
        unit ?: return null
        val provider = SemanticTokensListener(unit)
        return SemanticTokens(provider.data)
    }

    private class SemanticTokensListener(private val unit: CompilationUnit) : Listener() {
        val data = ArrayList<Int>()
        private var prevLine: Int = BASE_LINE
        private var prevColumn: Int = BASE_COLUMN

        private fun push(range: TextRange?, tokenType: Int, tokenModifiers: Int) {
            if (range == null) return
            val line = range.start.line - prevLine
            val column = if (range.start.line == prevLine) range.start.column - prevColumn else range.start.column
            val length = range.end.column - range.start.column
            prevLine = range.start.line
            prevColumn = range.start.column
            data.add(line)
            data.add(column)
            data.add(length)
            data.add(tokenType)
            data.add(tokenModifiers)
        }
    }
}
