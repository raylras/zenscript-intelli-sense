package raylras.zen.model.symbol.impl

import org.antlr.v4.runtime.ParserRuleContext
import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.CompilationUnit
import raylras.zen.model.isDzsUnit
import raylras.zen.model.isZsUnit
import raylras.zen.model.parser.ZenScriptParser.ClassDeclarationContext
import raylras.zen.model.resolve.resolveSymbols
import raylras.zen.model.symbol.ClassSymbol
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.type.ClassType
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

fun createClassSymbol(
    simpleNameCtx: ParserRuleContext?,
    ctx: ClassDeclarationContext,
    unit: CompilationUnit,
    callback: (ClassSymbol) -> Unit
) {
    simpleNameCtx ?: return
    ctx.classBody() ?: return
    callback(object : ClassSymbol, ParseTreeLocatable {
        override val simpleName: String by lazy { simpleNameCtx.text }

        override val qualifiedName by lazy {
            when {
                unit.isDzsUnit -> {
                    unit.qualifiedName
                }

                unit.isZsUnit -> {
                    unit.qualifiedName + '.' + simpleName
                }

                else -> {
                    "ERROR"
                }
            }
        }

        override val declaredMembers: Sequence<Symbol> by lazy {
            unit.scopeMap[ctx]?.getSymbols()
                ?.filter { it is ParseTreeLocatable }
                .orEmpty()
        }

        override val interfaces: Sequence<ClassSymbol> by lazy {
            ctx.qualifiedName()
                ?.flatMap { resolveSymbols<ClassSymbol>(it, unit) }
                ?.asSequence()
                .orEmpty()
        }

        override val type = ClassType(this)

        override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
            return declaredMembers + type.getExpands(env)
        }

        override val cst: ClassDeclarationContext = ctx

        override val unit: CompilationUnit = unit

        override val textRange: TextRange by lazy { ctx.textRange }

        override val simpleNameTextRange: TextRange by lazy { simpleNameCtx.textRange }

        override fun toString(): String = qualifiedName
    })
}
