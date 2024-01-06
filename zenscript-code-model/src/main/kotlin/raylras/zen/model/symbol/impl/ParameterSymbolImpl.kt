package raylras.zen.model.symbol.impl

import org.antlr.v4.runtime.ParserRuleContext
import raylras.zen.model.CompilationUnit
import raylras.zen.model.parser.ZenScriptParser.FormalParameterContext
import raylras.zen.model.parser.ZenScriptParser.FunctionExprContext
import raylras.zen.model.resolve.resolveTypes
import raylras.zen.model.symbol.Modifiable.Modifier
import raylras.zen.model.symbol.ParameterSymbol
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.type.*
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

fun createParameterSymbol(
    simpleNameCtx: ParserRuleContext?,
    ctx: FormalParameterContext,
    unit: CompilationUnit,
    callback: (ParameterSymbol) -> Unit
) {
    simpleNameCtx ?: return
    callback(object : ParameterSymbol, ParseTreeLocatable {
        override val isOptional: Boolean
            get() = ctx.defaultValue() != null

        override val isVararg: Boolean
            get() = ctx.varargsPrefix() != null

        override val simpleName: String
            get() = simpleNameCtx.text

        override val type: Type
            get() = getType(ctx, unit)

        override val modifier: Modifier
            get() = Modifier.IMPLICIT_VAL

        override val cst: FormalParameterContext
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

fun createParameterSymbol(
    name: String,
    type: Type,
    optional: Boolean = false,
    vararg: Boolean = false
): ParameterSymbol {
    return object : ParameterSymbol {
        override val isOptional = optional

        override val isVararg = vararg

        override val simpleName = name

        override val type = type

        override val modifier = Modifier.IMPLICIT_VAL

        override fun toString() = simpleName
    }
}

private fun getType(ctx: FormalParameterContext, unit: CompilationUnit): Type {
    when {
        ctx.typeLiteral() != null -> {
            return resolveTypes(ctx.typeLiteral(), unit).firstOrNull() ?: ErrorType
        }

        ctx.defaultValue() != null -> {
            return resolveTypes(ctx.typeLiteral(), unit).firstOrNull() ?: ErrorType
        }

        ctx.parent is FunctionExprContext -> {
            val index = (ctx.parent as FunctionExprContext).formalParameter().indexOf(ctx)
            return when (val parent =
                resolveTypes(ctx.parent, unit).firstOrNull()) {
                is FunctionType -> {
                    parent.parameterTypes[index]
                }

                is ClassType -> {
                    parent.firstAnonymousFunctionOrNull()
                        ?.parameters?.get(index)?.type
                        ?: ErrorType
                }

                else -> ErrorType
            }
        }

        else -> return ErrorType
    }
}
