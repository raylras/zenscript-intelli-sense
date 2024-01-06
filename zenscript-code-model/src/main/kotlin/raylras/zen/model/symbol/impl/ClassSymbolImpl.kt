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
        override val qualifiedName = when {
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

        override val declaredMembers: Sequence<Symbol>
            get() = unit.scopeMap[ctx]?.getSymbols()
                ?.filter { it is ParseTreeLocatable }
                ?: emptySequence()

        override val interfaces: Sequence<ClassSymbol>
            get() = ctx.qualifiedName()
                ?.flatMap { resolveSymbols<ClassSymbol>(it, unit) }
                ?.asSequence()
                ?: emptySequence()

        override val type = ClassType(this)

        override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
            return declaredMembers + type.getExpands(env)
        }

        override val simpleName: String
            get() = simpleNameCtx.text

        override val cst: ClassDeclarationContext
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
