package raylras.intellizen.symbol.impl

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
import raylras.intellizen.CompilationUnit
import raylras.intellizen.Visitor
import raylras.intellizen.parser.ZenScriptParser.*
import raylras.intellizen.resolve.resolveType
import raylras.intellizen.symbol.*
import raylras.intellizen.symbol.Modifiable.Modifier
import raylras.intellizen.type.*
import raylras.intellizen.util.TextPosition
import raylras.intellizen.util.TextRange
import raylras.intellizen.util.textRange

fun createVariableSymbol(
    simpleNameCtx: SimpleNameContext?,
    typeAnnotationCtx: TypeLiteralContext?,
    ctx: ParserRuleContext,
    unit: CompilationUnit,
    callback: (VariableSymbol) -> Unit
) {
    simpleNameCtx ?: return
    callback(object : VariableSymbol, TypeAnnotatable, ParseTreeLocatable {
        override val simpleName: String by lazy { simpleNameCtx.text }

        override val type: Type by lazy { getType(ctx, unit) }

        override val modifier: Modifier by lazy { ctx.accept(modifierResolver) }

        override val typeAnnotationCst: TypeLiteralContext? = typeAnnotationCtx

        override val typeAnnotationTextPosition: TextPosition by lazy { simpleNameCtx.textRange.end }

        override val cst: ParserRuleContext = ctx

        override val unit: CompilationUnit = unit

        override val textRange: TextRange by lazy { ctx.textRange }

        override val simpleNameTextRange: TextRange by lazy { simpleNameCtx.textRange }

        override fun toString(): String = simpleName
    })
}

fun createVariableSymbol(simpleName: String, type: Type, modifier: Modifier): VariableSymbol {
    return object : VariableSymbol {
        override val simpleName = simpleName

        override val type = type

        override val modifier = modifier
    }
}

private val modifierResolver = object : Visitor<Modifier>() {
    override fun visitVariableDeclaration(ctx: VariableDeclarationContext): Modifier {
        return when (ctx.prefix.type) {
            VAR -> Modifier.VAR
            VAL -> Modifier.VAL
            STATIC -> Modifier.STATIC
            GLOBAL -> Modifier.GLOBAL
            else -> Modifier.ERROR
        }
    }

    override fun visitForeachVariable(ctx: ForeachVariableContext): Modifier {
        return Modifier.IMPLICIT_VAR
    }
}

private fun getType(ctx: ParseTree, unit: CompilationUnit): Type {
    when (ctx) {
        is VariableDeclarationContext -> {
            return resolveType(ctx.typeLiteral(), unit)
                ?: resolveType(ctx.initializer, unit)
                ?: AnyType
        }

        is ForeachVariableContext -> {
            val statement = ctx.parent as ForeachStatementContext
            val variables = statement.foreachVariable()
            val exprType = resolveType(statement.expression(), unit)
            return when (val type = exprType?.applyUnaryOperator(Operator.FOR_IN, unit.env)) {
                is ListType -> when (variables.reversed().indexOf(ctx)) {
                    0 -> type.elementType
                    1 -> IntType
                    else -> AnyType
                }

                is ArrayType -> when (variables.reversed().indexOf(ctx)) {
                    0 -> type.elementType
                    1 -> IntType
                    else -> AnyType
                }

                is MapType -> when (variables.indexOf(ctx)) {
                    0 -> type.keyType
                    1 -> type.valueType
                    else -> AnyType
                }

                else -> AnyType
            }
        }

        else -> return AnyType
    }
}
