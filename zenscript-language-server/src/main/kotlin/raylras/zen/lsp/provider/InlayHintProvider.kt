package raylras.zen.lsp.provider

import org.antlr.v4.runtime.tree.ParseTree
import org.eclipse.lsp4j.InlayHint
import org.eclipse.lsp4j.InlayHintParams
import org.eclipse.lsp4j.jsonrpc.messages.Either
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Listener
import raylras.zen.model.brackets.BracketHandlers.getLocalizedNameLocal
import raylras.zen.model.brackets.BracketHandlers.getLocalizedNameRemote
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.resolve.resolveType
import raylras.zen.model.symbol.*
import raylras.zen.model.type.FunctionType
import raylras.zen.util.*

object InlayHintProvider {
    fun inlayHint(unit: CompilationUnit, params: InlayHintParams): List<InlayHint> {
        val visitor = InlayHintListener(unit, params.range.toTextRange())
        unit.accept(visitor)
        return visitor.result
    }
}

class InlayHintListener(private val unit: CompilationUnit, private val range: TextRange) : Listener() {
    val result = ArrayList<InlayHint>()

    override fun enterVariableDeclaration(ctx: VariableDeclarationContext) {
        checkRange(ctx.simpleName()) {
            val symbol = unit.symbolMap[ctx]
            checkTypeAnnotation<VariableSymbol>(symbol) { sym, pos ->
                addInlayHint(pos, " as ${sym.type.simpleTypeName}")
            }
        }
    }

    override fun enterFunctionDeclaration(ctx: FunctionDeclarationContext) {
        checkRange(ctx.PAREN_CLOSE()) {
            val symbol = unit.symbolMap[ctx]
            checkTypeAnnotation<FunctionSymbol>(symbol) { sym, pos ->
                addInlayHint(pos, " as ${sym.returnType.simpleTypeName}")
            }
        }
    }

    override fun enterFormalParameter(ctx: FormalParameterContext) {
        checkRange(ctx.simpleName()) {
            val symbol = unit.symbolMap[ctx]
            checkTypeAnnotation<ParameterSymbol>(symbol) { sym, pos ->
                addInlayHint(pos, " as ${sym.type.simpleTypeName}")
            }
        }
    }

    override fun enterForeachVariable(ctx: ForeachVariableContext) {
        checkRange(ctx.simpleName()) {
            val symbol = unit.symbolMap[ctx]
            checkTypeAnnotation<VariableSymbol>(symbol) { sym, pos ->
                addInlayHint(pos, ": ${sym.type.simpleTypeName}")
            }
        }
    }

    override fun enterFunctionExpr(ctx: FunctionExprContext) {
        checkRange(ctx.PAREN_CLOSE()) {
            if (ctx.typeLiteral() == null) {
                resolveType<FunctionType>(ctx, unit)?.returnType?.let { returnType ->
                    addInlayHint(ctx.PAREN_CLOSE().textRange.end, ": ${returnType.simpleTypeName}")
                }
            }
        }
    }

    override fun enterBracketHandlerExpr(ctx: BracketHandlerExprContext) {
        checkRange(ctx.GREATER_THEN()) {
            val expr = ctx.raw().text
            (getLocalizedNameLocal(expr, unit.env) ?: getLocalizedNameRemote(expr).getOrNull())
                ?.let { name ->
                    addInlayHint(ctx.textRange.end, ": $name")
                }
        }
    }

    private fun checkRange(ctx: ParseTree?, callback: (ParseTree) -> Unit) {
        if (ctx != null && ctx.textRange in range) {
            callback(ctx)
        }
    }

    private inline fun <reified T : Symbol> checkTypeAnnotation(
        symbol: Symbol?,
        callback: (T, pos: TextPosition) -> Unit
    ) {
        if (symbol is T && symbol is TypeAnnotatable && symbol.typeAnnotationCst == null) {
            callback(symbol, symbol.typeAnnotationTextPosition)
        }
    }

    private fun addInlayHint(pos: TextPosition, text: String) {
        result.add(InlayHint(pos.toLspPosition(), Either.forLeft(text)))
    }
}
