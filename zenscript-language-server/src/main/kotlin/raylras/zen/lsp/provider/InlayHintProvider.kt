package raylras.zen.lsp.provider

import org.eclipse.lsp4j.InlayHint
import org.eclipse.lsp4j.InlayHintParams
import org.eclipse.lsp4j.jsonrpc.messages.Either
import raylras.zen.model.CompilationUnit
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.symbol.TypeAnnotatable
import raylras.zen.model.symbol.VariableSymbol
import raylras.zen.util.toLspPosition

object InlayHintProvider {
    fun inlayHint(unit: CompilationUnit, params: InlayHintParams): List<InlayHint> {
        return unit.symbols
            .filter { shouldProvideTypeInlayHints(it) }
            .map {
                InlayHint(
                    (it as TypeAnnotatable).typeAnnotationTextPosition.toLspPosition(),
                    Either.forLeft(": " + it.type.simpleTypeName)
                )
            }
            .toList()
    }
}

fun shouldProvideTypeInlayHints(symbol: Symbol): Boolean {
    return when {
        symbol !is TypeAnnotatable -> false

        symbol.typeAnnotationCst != null -> false

        symbol is VariableSymbol -> {
            when ((symbol.cst as? VariableDeclarationContext)?.initializer) {
                is LiteralExprContext -> false
                is TypeCastExprContext -> false
                else -> true
            }
        }

        else -> true
    }
}
