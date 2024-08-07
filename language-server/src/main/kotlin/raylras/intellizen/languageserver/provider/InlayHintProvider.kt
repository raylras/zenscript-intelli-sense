package raylras.intellizen.languageserver.provider

import org.antlr.v4.runtime.tree.ParseTree
import org.eclipse.lsp4j.InlayHint
import org.eclipse.lsp4j.InlayHintParams
import org.eclipse.lsp4j.jsonrpc.messages.Either
import raylras.intellizen.CompilationUnit
import raylras.intellizen.Listener
import raylras.intellizen.brackets.BracketHandlers.getLocalizedNameLocal
import raylras.intellizen.languageserver.util.toLspPosition
import raylras.intellizen.languageserver.util.toTextRange
import raylras.intellizen.parser.ZenScriptParser.*
import raylras.intellizen.symbol.*
import raylras.intellizen.util.TextPosition
import raylras.intellizen.util.TextRange
import raylras.intellizen.util.contains
import raylras.intellizen.util.textRange
import java.util.concurrent.CompletableFuture

object InlayHintProvider {
    fun inlayHint(unit: CompilationUnit, params: InlayHintParams): List<InlayHint> {
        val listener = InlayHintListener(unit, params.range.toTextRange())
        unit.accept(listener)
        return listener.getResult()
    }
}

private class InlayHintListener(private val unit: CompilationUnit, private val range: TextRange) : Listener() {
    private val pendingList = mutableListOf<CompletableFuture<InlayHint?>>()

    fun getResult(): List<InlayHint> {
        return pendingList.mapNotNull { it.get() }
    }

    override fun enterVariableDeclaration(ctx: VariableDeclarationContext) {
        CompletableFuture.supplyAsync {
            if (ctx.simpleName().invalidRange()) return@supplyAsync null
            val symbol = unit.symbolMap[ctx] as? VariableSymbol
            symbol?.typeHintPos?.hint(" as ${symbol.type.simpleTypeName}")
        }.addToPendingList()
    }

    override fun enterFunctionDeclaration(ctx: FunctionDeclarationContext) {
        CompletableFuture.supplyAsync {
            if (ctx.PAREN_CLOSE().invalidRange()) return@supplyAsync null
            val symbol = unit.symbolMap[ctx] as? FunctionSymbol
            symbol?.typeHintPos?.hint(" as ${symbol.returnType.simpleTypeName}")
        }.addToPendingList()
    }

    override fun enterFormalParameter(ctx: FormalParameterContext) {
        CompletableFuture.supplyAsync {
            if (ctx.simpleName().invalidRange()) return@supplyAsync null
            val symbol = unit.symbolMap[ctx] as? ParameterSymbol
            symbol?.typeHintPos?.hint(" as ${symbol.type.simpleTypeName}")
        }.addToPendingList()
    }

    override fun enterForeachVariable(ctx: ForeachVariableContext) {
        CompletableFuture.supplyAsync {
            if (ctx.simpleName().invalidRange()) return@supplyAsync null
            val symbol = unit.symbolMap[ctx] as? VariableSymbol
            symbol?.typeHintPos?.hint(": ${symbol.type.simpleTypeName}")
        }.addToPendingList()
    }

    override fun enterFunctionExpr(ctx: FunctionExprContext) {
        CompletableFuture.supplyAsync {
            if (ctx.PAREN_CLOSE().invalidRange()) return@supplyAsync null
            val symbol = unit.symbolMap[ctx] as? FunctionSymbol
            symbol?.typeHintPos?.hint(": ${symbol.returnType.simpleTypeName}")
        }.addToPendingList()
    }

    override fun enterBracketHandlerExpr(ctx: BracketHandlerExprContext) {
        CompletableFuture.supplyAsync {
            if (ctx.GREATER_THEN().invalidRange()) return@supplyAsync null
            val expr = ctx.raw().text
            getLocalizedNameLocal(expr, unit.env)?.let { name ->
                ctx.GREATER_THEN().textRange.end.hint(": $name")
            }
        }.addToPendingList()
    }

    private fun ParseTree?.invalidRange(): Boolean {
        return this == null || this.textRange !in range
    }

    private val <T : Symbol> T.typeHintPos: TextPosition?
        get() = if (this is TypeAnnotatable && this.typeAnnotationCst == null) {
            this.typeAnnotationTextPosition
        } else {
            null
        }

    private fun TextPosition.hint(text: String): InlayHint {
        return InlayHint(this.toLspPosition(), Either.forLeft(text))
    }

    private fun CompletableFuture<InlayHint?>.addToPendingList() {
        pendingList.add(this)
    }
}
