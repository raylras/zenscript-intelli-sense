package raylras.zen.lsp.provider

import org.eclipse.lsp4j.InlayHint
import org.eclipse.lsp4j.InlayHintParams
import org.eclipse.lsp4j.jsonrpc.messages.Either
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Listener
import raylras.zen.model.brackets.BracketHandlers
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.resolve.resolveTypes
import raylras.zen.model.symbol.FunctionSymbol
import raylras.zen.model.symbol.ParameterSymbol
import raylras.zen.model.symbol.TypeAnnotatable
import raylras.zen.model.symbol.VariableSymbol
import raylras.zen.model.type.FunctionType
import raylras.zen.util.TextPosition
import raylras.zen.util.textRange
import raylras.zen.util.toLspPosition

object InlayHintProvider {
    fun inlayHint(unit: CompilationUnit, params: InlayHintParams): List<InlayHint> {
        val listener = InlayHintListener(unit)
        unit.accept(listener)
        return listener.result
    }
}

class InlayHintListener(private val unit: CompilationUnit) : Listener() {
    val result = ArrayList<InlayHint>()

    override fun enterVariableDeclaration(ctx: VariableDeclarationContext) {
        val symbol = unit.symbolMap[ctx]
        when {
            symbol !is VariableSymbol -> return
            symbol !is TypeAnnotatable -> return
            symbol.typeAnnotationCst != null -> return
            else -> addInlayHint(symbol.typeAnnotationTextPosition, " as ${symbol.type.simpleTypeName}")
        }
    }

    override fun enterFunctionDeclaration(ctx: FunctionDeclarationContext) {
        val symbol = unit.symbolMap[ctx]
        when {
            symbol !is FunctionSymbol -> return
            symbol !is TypeAnnotatable -> return
            symbol.typeAnnotationCst != null -> return
            else -> addInlayHint(symbol.typeAnnotationTextPosition, " as ${symbol.returnType.simpleTypeName}")
        }
    }

    override fun enterFormalParameter(ctx: FormalParameterContext) {
        val symbol = unit.symbolMap[ctx]
        when {
            symbol !is ParameterSymbol -> return
            symbol !is TypeAnnotatable -> return
            symbol.typeAnnotationCst != null -> return
            else -> addInlayHint(symbol.typeAnnotationTextPosition, " as ${symbol.type.simpleTypeName}")
        }
    }

    override fun enterForeachVariable(ctx: ForeachVariableContext) {
        val symbol = unit.symbolMap[ctx]
        when {
            symbol !is VariableSymbol -> return
            symbol !is TypeAnnotatable -> return
            symbol.typeAnnotationCst != null -> return
            else -> addInlayHint(symbol.typeAnnotationTextPosition, ": ${symbol.type.simpleTypeName}")
        }
    }

    override fun enterFunctionExpr(ctx: FunctionExprContext) {
        when {
            ctx.typeLiteral() != null -> return
            else -> {
                val returnType = resolveTypes(ctx, unit)
                    .filterIsInstance<FunctionType>()
                    .firstOrNull()
                    ?.returnType
                    ?: return
                addInlayHint(ctx.PAREN_CLOSE().textRange.end, ": ${returnType.simpleTypeName}")
            }
        }
    }

    override fun enterBracketHandlerExpr(ctx: BracketHandlerExprContext) {
        val expr = ctx.raw().text
        BracketHandlers.getLocalizedNameLocal(expr, unit.env)?.let { name ->
            addInlayHint(ctx.textRange.end, ": $name")
        }
    }

    private fun addInlayHint(pos: TextPosition, text: String) {
        result.add(InlayHint(pos.toLspPosition(), Either.forLeft(text)))
    }
}
