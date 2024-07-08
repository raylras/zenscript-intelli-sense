package raylras.intellizen.symbol.impl

import raylras.intellizen.CompilationUnit
import raylras.intellizen.Visitor
import raylras.intellizen.parser.ZenScriptParser.*
import raylras.intellizen.resolve.resolveType
import raylras.intellizen.symbol.FunctionSymbol
import raylras.intellizen.symbol.Modifiable.Modifier
import raylras.intellizen.symbol.ParameterSymbol
import raylras.intellizen.symbol.ParseTreeLocatable
import raylras.intellizen.symbol.TypeAnnotatable
import raylras.intellizen.type.AnyType
import raylras.intellizen.type.FunctionType
import raylras.intellizen.type.Type
import raylras.intellizen.util.TextPosition
import raylras.intellizen.util.TextRange
import raylras.intellizen.util.textRange

fun createFunctionSymbol(
    simpleNameCtx: SimpleNameContext?,
    ctx: FunctionDeclarationContext,
    unit: CompilationUnit,
    callback: (FunctionSymbol) -> Unit
) {
    callback(object : FunctionSymbol, TypeAnnotatable, ParseTreeLocatable {
        override val type: FunctionType by lazy { FunctionType(returnType, parameters.map { it.type }) }

        override val parameters: List<ParameterSymbol> by lazy { ctx.formalParameter().map { unit.symbolMap[it] as ParameterSymbol } }

        override val returnType: Type by lazy { resolveType(ctx.returnType(), unit) ?: AnyType }

        override val simpleName: String by lazy { simpleNameCtx?.text ?: "" }

        override val modifier: Modifier by lazy { ctx.accept(modifierResolver) }

        override val typeAnnotationCst: TypeLiteralContext? by lazy { ctx.returnType()?.typeLiteral() }

        override val typeAnnotationTextPosition: TextPosition by lazy { ctx.PAREN_CLOSE().textRange.end }

        override val cst: FunctionDeclarationContext = ctx

        override val unit: CompilationUnit = unit

        override val textRange: TextRange by lazy { ctx.textRange }

        override val simpleNameTextRange: TextRange by lazy { simpleNameCtx?.textRange ?: ctx.FUNCTION().textRange }

        override fun toString(): String = simpleName
    })
}

fun createFunctionSymbol(simpleName: String, returnType: Type, params: List<ParameterSymbol>): FunctionSymbol {
    return object : FunctionSymbol {
        override val type by lazy { FunctionType(returnType, params.map { it.type }) }

        override val parameters = params

        override val returnType = returnType

        override val simpleName = simpleName

        override val modifier = Modifier.ERROR

        override fun toString() = simpleName
    }
}

private val modifierResolver = object : Visitor<Modifier>() {
    override fun visitFunctionDeclaration(ctx: FunctionDeclarationContext): Modifier {
        return when {
            ctx.prefix != null -> {
                when (ctx.prefix.type) {
                    STATIC -> Modifier.STATIC
                    GLOBAL -> Modifier.GLOBAL
                    else -> Modifier.ERROR
                }
            }

            ctx.parent is TopLevelElementContext -> {
                Modifier.IMPLICIT_STATIC
            }

            else -> Modifier.ERROR
        }
    }
}
