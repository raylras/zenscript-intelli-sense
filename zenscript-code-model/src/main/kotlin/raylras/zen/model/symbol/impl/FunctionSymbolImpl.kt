package raylras.zen.model.symbol.impl

import raylras.zen.model.CompilationUnit
import raylras.zen.model.Visitor
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.resolve.resolveTypes
import raylras.zen.model.symbol.FunctionSymbol
import raylras.zen.model.symbol.Modifiable.Modifier
import raylras.zen.model.symbol.ParameterSymbol
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.symbol.TypeAnnotatable
import raylras.zen.model.type.AnyType
import raylras.zen.model.type.FunctionType
import raylras.zen.model.type.Type
import raylras.zen.util.TextPosition
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

fun createFunctionSymbol(
    simpleNameCtx: SimpleNameContext?,
    ctx: FunctionDeclarationContext,
    unit: CompilationUnit,
    callback: (FunctionSymbol) -> Unit
) {
    callback(object : FunctionSymbol, TypeAnnotatable, ParseTreeLocatable {
        override val type: FunctionType by lazy { FunctionType(returnType, parameters.map { it.type }) }

        override val parameters: List<ParameterSymbol> by lazy { ctx.formalParameter().map { unit.symbolMap[it] as ParameterSymbol } }

        override val returnType: Type by lazy { resolveTypes(ctx.returnType(), unit).firstOrNull() ?: AnyType }

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
