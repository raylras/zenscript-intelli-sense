package raylras.zen.lsp.provider

import org.eclipse.lsp4j.SemanticTokens
import org.eclipse.lsp4j.SemanticTokensParams
import raylras.zen.lsp.provider.data.TokenModifier
import raylras.zen.lsp.provider.data.TokenType
import raylras.zen.lsp.provider.data.tokenModifier
import raylras.zen.lsp.provider.data.tokenType
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Listener
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.resolve.resolveSymbols
import raylras.zen.model.symbol.Symbol
import raylras.zen.util.BASE_COLUMN
import raylras.zen.util.BASE_LINE
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

object SemanticTokensProvider {
    fun semanticTokensFull(unit: CompilationUnit, params: SemanticTokensParams): SemanticTokens {
        val provider = SemanticTokensListener(unit)
        unit.accept(provider)
        return SemanticTokens(provider.data)
    }
}

private class SemanticTokensListener(private val unit: CompilationUnit) : Listener() {
    val data = mutableListOf<Int>()
    private var prevLine: Int = BASE_LINE
    private var prevColumn: Int = BASE_COLUMN

    override fun exitVariableDeclaration(ctx: VariableDeclarationContext) {
        unit.symbolMap[ctx]?.let {
            push(ctx.simpleName().textRange, TokenType.VARIABLE, it.tokenModifier + TokenModifier.DECLARATION.bitflag)
        }
    }

    override fun exitFunctionDeclaration(ctx: FunctionDeclarationContext) {
        unit.symbolMap[ctx]?.let {
            push(ctx.simpleName().textRange, TokenType.FUNCTION, it.tokenModifier + TokenModifier.DECLARATION.bitflag)
        }
    }

    override fun exitFormalParameter(ctx: FormalParameterContext) {
        // Workaround: in VSCode, it seems that read-only Parameter are not highlighted as expected, but Variable working fine.
        push(
            ctx.simpleName().textRange,
            TokenType.VARIABLE,
            TokenModifier.READONLY.bitflag + TokenModifier.DECLARATION.bitflag
        )
    }

    override fun exitSimpleNameExpr(ctx: SimpleNameExprContext) {
        resolveSymbols<Symbol>(ctx, unit).firstOrNull()?.let {
            push(ctx.textRange, it.tokenType, it.tokenModifier)
        }
    }

    override fun exitMemberAccessExpr(ctx: MemberAccessExprContext) {
        resolveSymbols<Symbol>(ctx, unit).firstOrNull()?.let {
            push(ctx.simpleName().textRange, it.tokenType, it.tokenModifier)
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
