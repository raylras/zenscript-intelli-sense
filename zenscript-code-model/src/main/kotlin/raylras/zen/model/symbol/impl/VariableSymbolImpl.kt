package raylras.zen.model.symbol.impl

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Visitor
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.resolve.resolveTypes
import raylras.zen.model.symbol.Modifiable.Modifier
import raylras.zen.model.symbol.Operator
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.symbol.VariableSymbol
import raylras.zen.model.symbol.applyUnaryOperator
import raylras.zen.model.type.*
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

fun createVariableSymbol(
    simpleNameCtx: SimpleNameContext?,
    ctx: ParserRuleContext,
    unit: CompilationUnit,
    callback: (VariableSymbol) -> Unit
) {
    simpleNameCtx ?: return
    callback(object : VariableSymbol, ParseTreeLocatable {
        override val simpleName: String by lazy { simpleNameCtx.text }

        override val type: Type by lazy { getType(ctx, unit) }

        override val modifier: Modifier by lazy { ctx.accept(modifierResolver) }

        override val cst: ParserRuleContext = ctx

        override val unit: CompilationUnit = unit

        override val textRange: TextRange by lazy { ctx.textRange }

        override val selectionTextRange: TextRange by lazy { simpleNameCtx.textRange }

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
            return when {
                ctx.typeLiteral() != null -> {
                    resolveTypes(ctx.typeLiteral(), unit).firstOrNull() ?: ErrorType
                }

                ctx.initializer != null -> {
                    resolveTypes(ctx.initializer, unit).firstOrNull() ?: ErrorType
                }

                else -> {
                    ErrorType
                }
            }
        }

        override fun visitForeachVariable(ctx: ForeachVariableContext): Type {
            val statement = ctx.parent as? ForeachStatementContext ?: return ErrorType
            val variables = statement.foreachVariable()
            val exprType = resolveTypes(statement.expression(), unit).firstOrNull() ?: return ErrorType
            return when (val type = exprType.applyUnaryOperator(Operator.FOR_IN, unit.env)) {
                is ListType -> {
                    when (variables.size) {
                        // for v in expr
                        1 -> type.elementType

                        // for i, v in expr
                        2 -> {
                            when (variables.indexOf(ctx)) {
                                // case i
                                0 -> IntType

                                // case v
                                1 -> type.elementType

                                else -> ErrorType
                            }
                        }

                        else -> ErrorType
                    }
                }

                is ArrayType -> {
                    when (variables.size) {
                        // for v in expr
                        1 -> type.elementType

                        // for i, v in expr
                        2 -> {
                            when (variables.indexOf(ctx)) {
                                // case i
                                0 -> IntType

                                // case v
                                1 -> type.elementType

                                else -> ErrorType
                            }
                        }

                        else -> ErrorType
                    }
                }

                is MapType -> {
                    when (variables.size) {
                        // for v in expr
                        1 -> type.valueType

                        // for k, v in expr
                        2 -> {
                            when (variables.indexOf(ctx)) {
                                // case k
                                0 -> type.keyType

                                // case v
                                1 -> type.valueType

                                else -> ErrorType
                            }
                        }

                        else -> ErrorType
                    }
                }

                else -> ErrorType
            }
        }
    })
}
