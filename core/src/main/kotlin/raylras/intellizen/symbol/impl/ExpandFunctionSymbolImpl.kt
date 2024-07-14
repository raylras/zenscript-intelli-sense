package raylras.intellizen.symbol.impl

import org.antlr.v4.runtime.ParserRuleContext
import raylras.intellizen.CompilationUnit
import raylras.intellizen.parser.ZenScriptParser.ExpandFunctionDeclarationContext
import raylras.intellizen.resolve.resolveType
import raylras.intellizen.symbol.ExpandFunctionSymbol
import raylras.intellizen.symbol.ParameterSymbol
import raylras.intellizen.symbol.ParseTreeLocatable
import raylras.intellizen.type.AnyType
import raylras.intellizen.type.ErrorType
import raylras.intellizen.type.FunctionType
import raylras.intellizen.type.Type
import raylras.intellizen.util.TextRange
import raylras.intellizen.util.textRange

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
