package raylras.intellizen.symbol.impl

import org.antlr.v4.runtime.ParserRuleContext
import raylras.intellizen.CompilationUnit
import raylras.intellizen.parser.ZenScriptParser.OperatorFunctionDeclarationContext
import raylras.intellizen.resolve.resolveType
import raylras.intellizen.symbol.Operator
import raylras.intellizen.symbol.OperatorFunctionSymbol
import raylras.intellizen.symbol.ParameterSymbol
import raylras.intellizen.symbol.ParseTreeLocatable
import raylras.intellizen.type.AnyType
import raylras.intellizen.type.FunctionType
import raylras.intellizen.type.Type
import raylras.intellizen.util.TextRange
import raylras.intellizen.util.textRange

fun createOperatorFunctionSymbol(
    simpleNameCtx: ParserRuleContext?,
    ctx: OperatorFunctionDeclarationContext,
    unit: CompilationUnit,
    callback: (OperatorFunctionSymbol) -> Unit
) {
    simpleNameCtx ?: return
    callback(object : OperatorFunctionSymbol, ParseTreeLocatable {
        override val operator: Operator by lazy {
            Operator.of(
                simpleName,
                ctx.formalParameter()?.size ?: -1
            )
        }

        override val type: FunctionType by lazy { FunctionType(returnType, parameters.map { it.type }) }

        override val parameters: List<ParameterSymbol> by lazy { ctx.formalParameter().map { unit.symbolMap[it] as ParameterSymbol } }

        override val returnType: Type by lazy { resolveType(ctx.returnType(), unit) ?: AnyType }

        override val simpleName: String by lazy { simpleNameCtx.text }

        override val cst: OperatorFunctionDeclarationContext = ctx

        override val unit: CompilationUnit = unit

        override val textRange: TextRange by lazy { ctx.textRange }

        override val simpleNameTextRange: TextRange by lazy { simpleNameCtx.textRange }

        override fun toString(): String = simpleName
    })
}

fun createOperatorFunctionSymbol(
    operator: Operator,
    returnType: Type,
    params: List<ParameterSymbol> = emptyList()
): OperatorFunctionSymbol {
    return object : OperatorFunctionSymbol {
        override val operator: Operator = operator

        override val type: FunctionType by lazy { FunctionType(returnType, params.map { it.type }) }

        override val parameters: List<ParameterSymbol> = params

        override val returnType: Type = returnType

        override val simpleName: String = operator.literal

        override fun toString(): String = simpleName
    }
}
