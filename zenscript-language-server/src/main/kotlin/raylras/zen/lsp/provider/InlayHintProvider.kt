package raylras.zen.lsp.provider

import org.antlr.v4.runtime.tree.ParseTree
import org.eclipse.lsp4j.InlayHint
import org.eclipse.lsp4j.InlayHintParams
import org.eclipse.lsp4j.jsonrpc.messages.Either
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Listener
import raylras.zen.model.brackets.BracketHandlers.getLocalizedNameLocal
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.symbol.*
import raylras.zen.util.*
import java.util.concurrent.CompletableFuture

object InlayHintProvider {
    fun inlayHint(unit: CompilationUnit, params: InlayHintParams): List<InlayHint> {
        val listener = InlayHintListener(unit, params.range.toTextRange())
        unit.accept(listener)
        return listener.getResult()
    }
}

class InlayHintListener(private val unit: CompilationUnit, private val range: TextRange) : Listener() {
    private val pendingList = mutableListOf<CompletableFuture<InlayHint?>>()

    fun getResult(): List<InlayHint> {
        return pendingList.mapNotNull { it.get() }
    }

    override fun enterVariableDeclaration(ctx: VariableDeclarationContext) {
        CompletableFuture.supplyAsync {
            if (ctx.simpleName().invalidRange()) return@supplyAsync null
            val symbol = unit.symbolMap[ctx] as? VariableSymbol ?: return@supplyAsync null
            symbol.typeHintPos?.hint(" as ${symbol.type.simpleTypeName}")
        }.addToPendingList()
    }

    override fun enterFunctionDeclaration(ctx: FunctionDeclarationContext) {
        CompletableFuture.supplyAsync {
            if (ctx.PAREN_CLOSE().invalidRange()) return@supplyAsync null
            val symbol = unit.symbolMap[ctx] as? FunctionSymbol ?: return@supplyAsync null
            symbol.typeHintPos?.hint(" as ${symbol.returnType.simpleTypeName}")
        }.addToPendingList()
    }

    override fun enterFormalParameter(ctx: FormalParameterContext) {
        CompletableFuture.supplyAsync {
            if (ctx.simpleName().invalidRange()) return@supplyAsync null
            val symbol = unit.symbolMap[ctx] as? ParameterSymbol ?: return@supplyAsync null
            symbol.typeHintPos?.hint(" as ${symbol.type.simpleTypeName}")
        }.addToPendingList()
    }

    override fun enterForeachVariable(ctx: ForeachVariableContext) {
        CompletableFuture.supplyAsync {
            if (ctx.simpleName().invalidRange()) return@supplyAsync null
            val symbol = unit.symbolMap[ctx] as? VariableSymbol ?: return@supplyAsync null
            symbol.typeHintPos?.hint(": ${symbol.type.simpleTypeName}")
        }.addToPendingList()
    }

    override fun enterFunctionExpr(ctx: FunctionExprContext) {
        CompletableFuture.supplyAsync {
            if (ctx.PAREN_CLOSE().invalidRange()) return@supplyAsync null
            val symbol = unit.symbolMap[ctx] as? FunctionSymbol ?: return@supplyAsync null
            symbol.typeHintPos?.hint(": ${symbol.returnType.simpleTypeName}")
        }.addToPendingList()
    }

    override fun enterBracketHandlerExpr(ctx: BracketHandlerExprContext) {
        CompletableFuture.supplyAsync {
            if (ctx.GREATER_THEN().invalidRange()) return@supplyAsync null
            val expr = ctx.raw().text
            val name = getLocalizedNameLocal(expr, unit.env) ?: return@supplyAsync null
            ctx.GREATER_THEN().textRange.end.hint(": $name")
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
