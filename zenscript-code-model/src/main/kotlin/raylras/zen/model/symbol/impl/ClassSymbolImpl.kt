package raylras.zen.model.symbol.impl

import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.CompilationUnit
import raylras.zen.model.isDzsUnit
import raylras.zen.model.isZsUnit
import raylras.zen.model.parser.ZenScriptParser.ClassDeclarationContext
import raylras.zen.model.resolve.resolveTypes
import raylras.zen.model.symbol.ClassSymbol
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.symbol.isStatic
import raylras.zen.model.type.ClassType
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

fun createClassSymbol(
    ctx: ClassDeclarationContext,
    unit: CompilationUnit,
    callback: (ClassSymbol) -> Unit
) {
    ctx.simpleClassName() ?: return
    ctx.classBody() ?: return
    callback(object : ClassSymbol, ParseTreeLocatable {
        override val simpleName: String by lazy { ctx.simpleClassName().text }

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

        override val interfaces: Sequence<ClassType> by lazy {
            ctx.qualifiedName()
                ?.flatMap { resolveTypes<ClassType>(it, unit) }
                ?.asSequence()
                .orEmpty()
        }

        override val type = ClassType(this)

        override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
            return declaredMembers.filter { it.isStatic }
        }

        override val cst: ClassDeclarationContext = ctx

        override val unit: CompilationUnit = unit

        override val textRange: TextRange by lazy { ctx.textRange }

        override val simpleNameTextRange: TextRange by lazy { ctx.simpleClassName().textRange }

        override fun toString(): String = qualifiedName
    })
}
