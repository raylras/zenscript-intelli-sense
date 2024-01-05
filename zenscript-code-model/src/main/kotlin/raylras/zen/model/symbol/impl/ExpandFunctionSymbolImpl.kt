package raylras.zen.model.symbol.impl

import org.antlr.v4.runtime.ParserRuleContext
import raylras.zen.model.CompilationUnit
import raylras.zen.model.parser.ZenScriptParser.ExpandFunctionDeclarationContext
import raylras.zen.model.resolve.getType
import raylras.zen.model.symbol.ExpandFunctionSymbol
import raylras.zen.model.symbol.ParameterSymbol
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.type.AnyType
import raylras.zen.model.type.FunctionType
import raylras.zen.model.type.Type
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

fun createExpandFunctionSymbol(
    simpleNameCtx: ParserRuleContext?,
    ctx: ExpandFunctionDeclarationContext,
    unit: CompilationUnit,
    callback: (ExpandFunctionSymbol) -> Unit
) {
    simpleNameCtx ?: return
    ctx.typeLiteral() ?: return
    ctx.functionBody() ?: return
    callback(object : ExpandFunctionSymbol, ParseTreeLocatable {
        override val parameters: List<ParameterSymbol>
            get() = ctx.formalParameter().map { unit.symbolMap[it] as ParameterSymbol }

        override val returnType: Type
            get() = type.returnType

        override val expandingType: Type
            get() = getType(cst.typeLiteral(), unit)

        override val simpleName: String
            get() = simpleNameCtx.text

        override val type: FunctionType
            get() = getType(cst, unit)
                .takeIf { it is FunctionType }
                ?.let { it as FunctionType }
                ?: FunctionType(AnyType)

        override val cst: ExpandFunctionDeclarationContext
            get() = ctx

        override val unit: CompilationUnit
            get() = unit

        override val textRange: TextRange
            get() = cst.textRange

        override val selectionTextRange: TextRange
            get() = simpleNameCtx.textRange

        override fun toString(): String {
            return simpleName
        }
    })
}
