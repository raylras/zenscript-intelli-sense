package raylras.zen.lsp.provider

import org.antlr.v4.runtime.tree.RuleNode
import org.eclipse.lsp4j.InlayHint
import org.eclipse.lsp4j.InlayHintParams
import org.eclipse.lsp4j.jsonrpc.messages.Either
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Visitor
import raylras.zen.model.brackets.BracketHandlers
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.resolve.resolveType
import raylras.zen.model.symbol.FunctionSymbol
import raylras.zen.model.symbol.ParameterSymbol
import raylras.zen.model.symbol.TypeAnnotatable
import raylras.zen.model.symbol.VariableSymbol
import raylras.zen.model.type.FunctionType
import raylras.zen.util.*

object InlayHintProvider {
    fun inlayHint(unit: CompilationUnit, params: InlayHintParams): List<InlayHint> {
        val visitor = InlayHintVisitor(unit, params.range.toTextRange())
        unit.accept(visitor)
        return visitor.result
    }
}

class InlayHintVisitor(private val unit: CompilationUnit, private val range: TextRange) : Visitor<Unit>() {
    val result = ArrayList<InlayHint>()

    override fun visitChildren(node: RuleNode) {
        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            if (child.textRange in range) {
                child.accept(this)
            }
        }
    }

    override fun visitVariableDeclaration(ctx: VariableDeclarationContext) {
        if (ctx.textRange !in range) return
        val symbol = unit.symbolMap[ctx]
        when {
            symbol !is VariableSymbol -> return
            symbol !is TypeAnnotatable -> return
            symbol.typeAnnotationCst != null -> return
            else -> addInlayHint(symbol.typeAnnotationTextPosition, " as ${symbol.type.simpleTypeName}")
        }
        visitChildren(ctx)
    }

    override fun visitFunctionDeclaration(ctx: FunctionDeclarationContext) {
        if (ctx.textRange !in range) return
        val symbol = unit.symbolMap[ctx]
        when {
            symbol !is FunctionSymbol -> return
            symbol !is TypeAnnotatable -> return
            symbol.typeAnnotationCst != null -> return
            else -> addInlayHint(symbol.typeAnnotationTextPosition, " as ${symbol.returnType.simpleTypeName}")
        }
        visitChildren(ctx)
    }

    override fun visitFormalParameter(ctx: FormalParameterContext) {
        if (ctx.textRange !in range) return
        val symbol = unit.symbolMap[ctx]
        when {
            symbol !is ParameterSymbol -> return
            symbol !is TypeAnnotatable -> return
            symbol.typeAnnotationCst != null -> return
            else -> addInlayHint(symbol.typeAnnotationTextPosition, " as ${symbol.type.simpleTypeName}")
        }
    }

    override fun visitForeachVariable(ctx: ForeachVariableContext) {
        if (ctx.textRange !in range) return
        val symbol = unit.symbolMap[ctx]
        when {
            symbol !is VariableSymbol -> return
            symbol !is TypeAnnotatable -> return
            symbol.typeAnnotationCst != null -> return
            else -> addInlayHint(symbol.typeAnnotationTextPosition, ": ${symbol.type.simpleTypeName}")
        }
    }

    override fun visitFunctionExpr(ctx: FunctionExprContext) {
        if (ctx.textRange !in range) return
        when {
            ctx.typeLiteral() != null -> return
            else -> {
                resolveType<FunctionType>(ctx, unit)?.returnType?.let { returnType ->
                    addInlayHint(ctx.PAREN_CLOSE().textRange.end, ": ${returnType.simpleTypeName}")
                }
            }
        }
        visitChildren(ctx)
    }

    override fun visitBracketHandlerExpr(ctx: BracketHandlerExprContext) {
        val expr = ctx.raw().text
        BracketHandlers.getLocalizedNameLocal(expr, unit.env)?.let { name ->
            addInlayHint(ctx.textRange.end, ": $name")
        }
    }

    private fun addInlayHint(pos: TextPosition, text: String) {
        result.add(InlayHint(pos.toLspPosition(), Either.forLeft(text)))
    }
}
