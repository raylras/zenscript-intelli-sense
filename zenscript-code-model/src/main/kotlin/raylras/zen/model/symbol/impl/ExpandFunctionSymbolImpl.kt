package raylras.zen.model.symbol.impl

import org.antlr.v4.runtime.ParserRuleContext
import raylras.zen.model.CompilationUnit
import raylras.zen.model.parser.ZenScriptParser.ExpandFunctionDeclarationContext
import raylras.zen.model.resolve.resolveType
import raylras.zen.model.symbol.ExpandFunctionSymbol
import raylras.zen.model.symbol.ParameterSymbol
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.type.AnyType
import raylras.zen.model.type.ErrorType
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
        override val parameters: List<ParameterSymbol> by lazy { ctx.formalParameter().map { unit.symbolMap[it] as ParameterSymbol } }

        override val returnType: Type by lazy { resolveType(ctx.returnType(), unit) ?: AnyType }

        override val expandingType: Type by lazy { resolveType(cst.typeLiteral(), unit) ?: ErrorType }

        override val simpleName: String by lazy { simpleNameCtx.text }

        override val type: FunctionType by lazy { FunctionType(returnType, parameters.map { it.type }) }

        override val cst: ExpandFunctionDeclarationContext = ctx

        override val unit: CompilationUnit = unit

        override val textRange: TextRange by lazy { cst.textRange }

        override val simpleNameTextRange: TextRange by lazy { simpleNameCtx.textRange }

        override fun toString(): String = simpleName
    })
}
