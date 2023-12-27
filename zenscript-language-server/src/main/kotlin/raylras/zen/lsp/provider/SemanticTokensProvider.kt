package raylras.zen.lsp.provider

import org.eclipse.lsp4j.SemanticTokens
import org.eclipse.lsp4j.SemanticTokensParams
import raylras.zen.lsp.TokenType
import raylras.zen.lsp.tokenModifier
import raylras.zen.lsp.tokenType
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Listener
import raylras.zen.model.parser.ZenScriptParser.SimpleNameExprContext
import raylras.zen.model.resolve.lookupSymbol
import raylras.zen.model.symbol.Modifiable
import raylras.zen.util.BASE_COLUMN
import raylras.zen.util.BASE_LINE
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

object SemanticTokensProvider {
    fun semanticTokensFull(unit: CompilationUnit?, params: SemanticTokensParams): SemanticTokens? {
        unit ?: return null
        val provider = SemanticTokensListener(unit)
        unit.accept(provider)
        return SemanticTokens(provider.data)
    }

    private class SemanticTokensListener(private val unit: CompilationUnit) : Listener() {
        val data = ArrayList<Int>()

        private var prevLine: Int = BASE_LINE
        private var prevColumn: Int = BASE_COLUMN

        override fun enterSimpleNameExpr(ctx: SimpleNameExprContext) {
            val lookupSymbol = lookupSymbol(ctx, unit)
            lookupSymbol.firstOrNull()?.let {
                when {
                    it is Modifiable -> push(ctx.textRange, it.tokenType, it.tokenModifier)
                }
            }
        }

        private fun push(range: TextRange?, tokenType: TokenType?, tokenModifiers: Int) {
            range ?: return
            tokenType ?: return
            val line = range.start.line - prevLine
            val column = if (range.start.line == prevLine) range.start.column - prevColumn else range.start.column
            val length = range.end.column - range.start.column
            prevLine = range.start.line
            prevColumn = range.start.column
            data.add(line)
            data.add(column)
            data.add(length)
            data.add(tokenType.ordinal)
            data.add(tokenModifiers)
        }
    }
}
