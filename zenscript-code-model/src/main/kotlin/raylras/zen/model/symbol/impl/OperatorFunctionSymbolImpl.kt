package raylras.zen.model.symbol.impl

import org.antlr.v4.runtime.ParserRuleContext
import raylras.zen.model.CompilationUnit
import raylras.zen.model.parser.ZenScriptParser.OperatorFunctionDeclarationContext
import raylras.zen.model.resolve.resolveTypes
import raylras.zen.model.symbol.Operator
import raylras.zen.model.symbol.OperatorFunctionSymbol
import raylras.zen.model.symbol.ParameterSymbol
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.type.ErrorType
import raylras.zen.model.type.FunctionType
import raylras.zen.model.type.Type
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

fun createOperatorFunctionSymbol(
    simpleNameCtx: ParserRuleContext?,
    ctx: OperatorFunctionDeclarationContext,
    unit: CompilationUnit,
    callback: (OperatorFunctionSymbol) -> Unit
) {
    simpleNameCtx ?: return
    callback(object : OperatorFunctionSymbol, ParseTreeLocatable {
        override val operator: Operator
            get() = Operator.of(
                simpleName,
                ctx.formalParameter()?.size ?: -1
            )

        override val type: FunctionType
            get() = FunctionType(returnType, parameters.map { it.type })

        override val parameters: List<ParameterSymbol>
            get() = ctx.formalParameter().map { unit.symbolMap[it] as ParameterSymbol }

        override val returnType: Type
            get() = resolveTypes(ctx.returnType(), unit).firstOrNull() ?: ErrorType

        override val simpleName: String
            get() = simpleNameCtx.text

        override val cst: OperatorFunctionDeclarationContext
            get() = ctx

        override val unit: CompilationUnit
            get() = unit

        override val textRange: TextRange
            get() = ctx.textRange

        override val selectionTextRange: TextRange
            get() = simpleNameCtx.textRange

        override fun toString(): String {
            return simpleName
        }
    })
}

fun createOperatorFunctionSymbol(
    operator: Operator,
    returnType: Type,
    params: List<ParameterSymbol> = emptyList()
): OperatorFunctionSymbol {
    return object : OperatorFunctionSymbol {
        override val operator: Operator
            get() = operator

        override val type: FunctionType
            get() = FunctionType(returnType, params.map { it.type })

        override val parameters: List<ParameterSymbol>
            get() = params

        override val returnType: Type
            get() = returnType

        override val simpleName: String
            get() = operator.literal

        override fun toString(): String {
            return simpleName
        }
    }
}
