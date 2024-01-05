package raylras.zen.model.symbol.impl

import raylras.zen.model.CompilationUnit
import raylras.zen.model.Visitor
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.resolve.getType
import raylras.zen.model.symbol.FunctionSymbol
import raylras.zen.model.symbol.Modifiable.Modifier
import raylras.zen.model.symbol.ParameterSymbol
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.type.AnyType
import raylras.zen.model.type.FunctionType
import raylras.zen.model.type.Type
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

fun createFunctionSymbol(
    simpleNameCtx: SimpleNameContext?,
    ctx: FunctionDeclarationContext,
    unit: CompilationUnit,
    callback: (FunctionSymbol) -> Unit
) {
    callback(object : FunctionSymbol, ParseTreeLocatable {
        override val type: FunctionType
            get() = getType(ctx, unit)
                .takeIf { it is FunctionType }
                ?.let { it as FunctionType }
                ?: FunctionType(AnyType)

        override val parameters: List<ParameterSymbol>
            get() = ctx.formalParameter().map { unit.symbolMap[it] as ParameterSymbol }

        override val returnType: Type
            get() = type.returnType

        override val simpleName: String
            get() = simpleNameCtx?.text ?: ""

        override val modifier: Modifier
            get() = ctx.accept(modifierResolver)

        override val cst: FunctionDeclarationContext
            get() = ctx

        override val unit: CompilationUnit
            get() = unit

        override val textRange: TextRange
            get() = ctx.textRange

        override val selectionTextRange: TextRange
            get() = simpleNameCtx?.textRange ?: ctx.FUNCTION().textRange

        override fun toString(): String {
            return simpleName
        }
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
