package raylras.zen.model.symbol.impl

import raylras.zen.model.CompilationUnit
import raylras.zen.model.parser.ZenScriptParser.ConstructorDeclarationContext
import raylras.zen.model.resolve.getType
import raylras.zen.model.symbol.ClassSymbol
import raylras.zen.model.symbol.ConstructorSymbol
import raylras.zen.model.symbol.ParameterSymbol
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.type.AnyType
import raylras.zen.model.type.FunctionType
import raylras.zen.model.type.Type
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

fun createConstructorSymbol(
    ctx: ConstructorDeclarationContext,
    unit: CompilationUnit,
    declaringClass: ClassSymbol?,
    callback: (ConstructorSymbol) -> Unit
) {
    declaringClass ?: return
    ctx.constructorBody() ?: return
    callback(object : ConstructorSymbol, ParseTreeLocatable {
        override val declaringClass: ClassSymbol
            get() = declaringClass

        override val parameters: List<ParameterSymbol>
            get() = ctx.formalParameter().map { unit.symbolMap[it] as ParameterSymbol }

        override val returnType: Type
            get() = type.returnType

        override val simpleName: String
            get() = ctx.ZEN_CONSTRUCTOR().text

        override val type: FunctionType
            get() = getType(ctx, unit)
                .takeIf { it is FunctionType }
                ?.let { it as FunctionType }
                ?: FunctionType(AnyType)

        override val cst: ConstructorDeclarationContext
            get() = ctx

        override val unit: CompilationUnit
            get() = unit

        override val textRange: TextRange
            get() = ctx.textRange

        override val selectionTextRange: TextRange
            get() = ctx.ZEN_CONSTRUCTOR().textRange

        override fun toString(): String {
            return simpleName
        }
    })
}
