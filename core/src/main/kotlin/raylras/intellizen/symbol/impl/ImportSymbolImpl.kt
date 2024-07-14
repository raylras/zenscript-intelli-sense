package raylras.intellizen.symbol.impl

import raylras.intellizen.CompilationEnvironment
import raylras.intellizen.CompilationUnit
import raylras.intellizen.parser.ZenScriptParser.ImportDeclarationContext
import raylras.intellizen.symbol.ImportSymbol
import raylras.intellizen.symbol.ParseTreeLocatable
import raylras.intellizen.symbol.Symbol
import raylras.intellizen.type.VoidType
import raylras.intellizen.util.TextRange
import raylras.intellizen.util.textRange

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
            return unit.env.lookupSymbols(ctx.qualifiedName().text)
        }

        override val cst: ImportDeclarationContext = ctx

        override val unit: CompilationUnit = unit

        override val textRange: TextRange by lazy { ctx.textRange }

        override val simpleNameTextRange: TextRange by lazy { simpleNameCtx.textRange }

        override fun toString(): String = qualifiedName
    })
}
