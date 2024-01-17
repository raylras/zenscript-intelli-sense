package raylras.zen.model.symbol.impl

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Visitor
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.resolve.resolveType
import raylras.zen.model.symbol.*
import raylras.zen.model.symbol.Modifiable.Modifier
import raylras.zen.model.type.*
import raylras.zen.util.TextPosition
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

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
    return ctx.accept(object : Visitor<Type>() {
        override fun visitVariableDeclaration(ctx: VariableDeclarationContext): Type {
            return resolveType(ctx.typeLiteral(), unit)
                ?: resolveType(ctx.initializer, unit)
                ?: AnyType
        }

        override fun visitForeachVariable(ctx: ForeachVariableContext): Type {
            val statement = ctx.parent as? ForeachStatementContext ?: return AnyType
            val variables = statement.foreachVariable()
            val exprType = resolveType<Type>(statement.expression(), unit) ?: return AnyType
            return when (val type = exprType.applyUnaryOperator(Operator.FOR_IN, unit.env)) {
                is ListType -> {
                    when (variables.size) {
                        // for element in list
                        1 -> type.elementType

                        // for index, element in list
                        2 -> {
                            when (variables.indexOf(ctx)) {
                                // case index
                                0 -> IntType

                                // case element
                                1 -> type.elementType

                                else -> AnyType
                            }
                        }

                        else -> AnyType
                    }
                }

                is ArrayType -> {
                    when (variables.size) {
                        // for element in array
                        1 -> type.elementType

                        // for index, element in array
                        2 -> {
                            when (variables.indexOf(ctx)) {
                                // case index
                                0 -> IntType

                                // case element
                                1 -> type.elementType

                                else -> AnyType
                            }
                        }

                        else -> AnyType
                    }
                }

                is MapType -> {
                    when (variables.size) {
                        // for key in map
                        1 -> type.keyType

                        // for key, value in map
                        2 -> {
                            when (variables.indexOf(ctx)) {
                                // case key
                                0 -> type.keyType

                                // case value
                                1 -> type.valueType

                                else -> AnyType
                            }
                        }

                        else -> AnyType
                    }
                }

                else -> AnyType
            }
        }
    })
}
