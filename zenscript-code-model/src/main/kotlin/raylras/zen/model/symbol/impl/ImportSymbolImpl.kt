package raylras.zen.model.symbol.impl

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
    ctx: ImportDeclarationContext,
    unit: CompilationUnit,
    callback: (ImportSymbol) -> Unit
) {
    val simpleNameCtx = ctx.alias ?: ctx.qualifiedName()?.simpleName()?.last() ?: return
    callback(object : ImportSymbol, ParseTreeLocatable {
        override val qualifiedName: String by lazy { ctx.qualifiedName().text }

        override val simpleName: String by lazy { simpleNameCtx.text }

        override val type: VoidType = VoidType

        override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
            return resolveSymbols(ctx, unit)
        }

        override val cst: ImportDeclarationContext = ctx

        override val unit: CompilationUnit = unit

        override val textRange: TextRange by lazy { ctx.textRange }

        override val simpleNameTextRange: TextRange by lazy { simpleNameCtx.textRange }

        override fun toString(): String = qualifiedName
    })
}
