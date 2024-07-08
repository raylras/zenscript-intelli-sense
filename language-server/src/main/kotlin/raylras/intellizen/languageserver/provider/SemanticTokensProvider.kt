package raylras.intellizen.languageserver.provider

import org.antlr.v4.runtime.tree.ParseTree
import org.eclipse.lsp4j.SemanticTokens
import org.eclipse.lsp4j.SemanticTokensRangeParams
import raylras.intellizen.CompilationUnit
import raylras.intellizen.Listener
import raylras.intellizen.languageserver.provider.data.TokenModifier
import raylras.intellizen.languageserver.provider.data.TokenType
import raylras.intellizen.languageserver.provider.data.tokenModifier
import raylras.intellizen.languageserver.provider.data.tokenType
import raylras.intellizen.languageserver.util.toTextRange
import raylras.intellizen.parser.ZenScriptParser.*
import raylras.intellizen.resolve.resolveSymbol
import raylras.intellizen.util.*

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
            resolveSymbol(ctx, unit)?.let {
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
            resolveSymbol(ctx, unit)?.let {
                push(ctx.textRange, it.tokenType, it.tokenModifier)
            }
        }
    }

    override fun exitMemberAccessExpr(ctx: MemberAccessExprContext) {
        checkRange(ctx.simpleName()) { simpleName ->
            resolveSymbol(ctx, unit)?.let {
                push(simpleName.textRange, it.tokenType, it.tokenModifier)
            }
        }
    }

    private fun checkRange(ctx: ParseTree?, callback: (ParseTree) -> Unit) {
        if (ctx != null && ctx.textRange in range) {
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
