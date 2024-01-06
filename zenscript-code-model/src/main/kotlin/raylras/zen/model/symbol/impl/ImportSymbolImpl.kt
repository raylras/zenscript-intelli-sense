package raylras.zen.model.symbol.impl

import org.antlr.v4.runtime.ParserRuleContext
import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.CompilationUnit
import raylras.zen.model.parser.ZenScriptParser.ImportDeclarationContext
import raylras.zen.model.resolve.resolveSymbols
import raylras.zen.model.symbol.ImportSymbol
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.type.VoidType
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

fun createImportSymbol(
    simpleNameCtx: ParserRuleContext?,
    ctx: ImportDeclarationContext,
    unit: CompilationUnit,
    callback: (ImportSymbol) -> Unit
) {
    simpleNameCtx ?: return
    ctx.qualifiedName() ?: return
    callback(object : ImportSymbol, ParseTreeLocatable {
        override val qualifiedName: String
            get() = ctx.qualifiedName().text

        override val simpleName: String
            get() = simpleNameCtx.text

        override val type: VoidType
            get() = VoidType

        override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
            return resolveSymbols(ctx, unit)
        }

        override val cst: ImportDeclarationContext
            get() = ctx

        override val unit: CompilationUnit
            get() = unit

        override val textRange: TextRange
            get() = ctx.textRange

        override val selectionTextRange: TextRange
            get() = simpleNameCtx.textRange

        override fun toString(): String {
            return qualifiedName
        }
    })
}
