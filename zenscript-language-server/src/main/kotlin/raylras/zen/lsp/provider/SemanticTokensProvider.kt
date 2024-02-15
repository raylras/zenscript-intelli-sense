package raylras.zen.lsp.provider

import org.antlr.v4.runtime.tree.ParseTree
import org.eclipse.lsp4j.SemanticTokens
import org.eclipse.lsp4j.SemanticTokensRangeParams
import raylras.zen.lsp.provider.data.TokenModifier
import raylras.zen.lsp.provider.data.TokenType
import raylras.zen.lsp.provider.data.tokenModifier
import raylras.zen.lsp.provider.data.tokenType
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Listener
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.resolve.resolveSymbols
import raylras.zen.model.symbol.Symbol
import raylras.zen.util.*

object SemanticTokensProvider {
    fun semanticTokensRange(unit: CompilationUnit, params: SemanticTokensRangeParams): SemanticTokens {
        val listener = SemanticTokensListener(unit, params.range.toTextRange())
        unit.accept(listener)
        return SemanticTokens(listener.result)
    }
}

private class SemanticTokensListener(private val unit: CompilationUnit, private val range: TextRange) : Listener() {
    val result = mutableListOf<Int>()
    private var prevLine: Int = BASE_LINE
    private var prevColumn: Int = BASE_COLUMN

    override fun enterQualifiedName(ctx: QualifiedNameContext) {
        checkRange(ctx) {
            resolveSymbols<Symbol>(ctx, unit).firstOrNull()?.let {
                push(ctx.simpleName().last().textRange, it.tokenType, it.tokenModifier)
            }
        }
    }

    override fun exitVariableDeclaration(ctx: VariableDeclarationContext) {
        checkRange(ctx.simpleName()) { simpleName ->
            unit.symbolMap[simpleName]?.let {
                push(simpleName.textRange, TokenType.VARIABLE, it.tokenModifier + TokenModifier.DECLARATION.bitflag)
            }
        }
    }

    override fun exitFunctionDeclaration(ctx: FunctionDeclarationContext) {
        checkRange(ctx.simpleName()) { simpleName ->
            unit.symbolMap[simpleName]?.let {
                push(simpleName.textRange, TokenType.FUNCTION, it.tokenModifier + TokenModifier.DECLARATION.bitflag)
            }
        }
    }

    override fun exitFormalParameter(ctx: FormalParameterContext) {
        checkRange(ctx.simpleName()) { simpleName ->
            push(
                simpleName.textRange,
                // Workaround: in VSCode, it seems that read-only Parameter are not highlighted as expected, but Variable working fine.
                TokenType.VARIABLE,
                TokenModifier.READONLY.bitflag + TokenModifier.DECLARATION.bitflag
            )
        }
    }

    override fun exitSimpleNameExpr(ctx: SimpleNameExprContext) {
        checkRange(ctx) {
            resolveSymbols<Symbol>(ctx, unit).firstOrNull()?.let {
                push(ctx.textRange, it.tokenType, it.tokenModifier)
            }
        }
    }

    override fun exitMemberAccessExpr(ctx: MemberAccessExprContext) {
        checkRange(ctx.simpleName()) { simpleName ->
            resolveSymbols<Symbol>(ctx, unit).firstOrNull()?.let {
                push(simpleName.textRange, it.tokenType, it.tokenModifier)
            }
        }
    }

    private fun checkRange(ctx: ParseTree, callback: (ParseTree) -> Unit) {
        if (ctx.textRange in range) {
            callback(ctx)
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
        result.add(line)
        result.add(column)
        result.add(length)
        result.add(tokenType.ordinal)
        result.add(tokenModifiers)
    }
}
