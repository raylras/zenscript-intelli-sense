package raylras.zen.model.symbol.impl

import org.antlr.v4.runtime.ParserRuleContext
import raylras.zen.model.CompilationUnit
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.resolve.resolveTypes
import raylras.zen.model.symbol.Modifiable.Modifier
import raylras.zen.model.symbol.ParameterSymbol
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.symbol.TypeAnnotatable
import raylras.zen.model.type.*
import raylras.zen.util.TextPosition
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

fun createParameterSymbol(
    simpleNameCtx: ParserRuleContext?,
    ctx: FormalParameterContext,
    unit: CompilationUnit,
    callback: (ParameterSymbol) -> Unit
) {
    simpleNameCtx ?: return
    callback(object : ParameterSymbol, TypeAnnotatable, ParseTreeLocatable {
        override val isOptional: Boolean by lazy { ctx.defaultValue() != null }

        override val isVararg: Boolean by lazy { ctx.varargsPrefix() != null }

        override val simpleName: String by lazy { simpleNameCtx.text }

        override val type: Type by lazy { getType(ctx, unit) }

        override val modifier: Modifier = Modifier.IMPLICIT_VAL

        override val typeAnnotationCst: TypeLiteralContext? by lazy { ctx.typeLiteral() }

        override val typeAnnotationTextPosition: TextPosition by lazy { simpleNameTextRange.end }

        override val cst: FormalParameterContext = ctx

        override val unit: CompilationUnit = unit

        override val textRange: TextRange by lazy { ctx.textRange }

        override val simpleNameTextRange: TextRange by lazy { simpleNameCtx.textRange }

        override fun toString(): String = simpleName
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
