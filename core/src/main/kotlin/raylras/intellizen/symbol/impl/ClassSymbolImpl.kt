package raylras.intellizen.symbol.impl

import raylras.intellizen.CompilationEnvironment
import raylras.intellizen.CompilationUnit
import raylras.intellizen.isDzsUnit
import raylras.intellizen.isZsUnit
import raylras.intellizen.parser.ZenScriptParser.ClassDeclarationContext
import raylras.intellizen.resolve.resolveType
import raylras.intellizen.symbol.ClassSymbol
import raylras.intellizen.symbol.ParseTreeLocatable
import raylras.intellizen.symbol.Symbol
import raylras.intellizen.symbol.isStatic
import raylras.intellizen.type.ClassType
import raylras.intellizen.util.TextRange
import raylras.intellizen.util.textRange

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
                ?.mapNotNull { resolveType(it, unit) as? ClassType }
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
