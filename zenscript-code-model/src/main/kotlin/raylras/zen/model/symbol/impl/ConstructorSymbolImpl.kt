package raylras.zen.model.symbol.impl

import raylras.zen.model.CompilationUnit
import raylras.zen.model.parser.ZenScriptParser.ConstructorDeclarationContext
import raylras.zen.model.symbol.ClassSymbol
import raylras.zen.model.symbol.ConstructorSymbol
import raylras.zen.model.symbol.ParameterSymbol
import raylras.zen.model.symbol.ParseTreeLocatable
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
        override val declaringClass: ClassSymbol = declaringClass

        override val parameters: List<ParameterSymbol> by lazy { ctx.formalParameter().map { unit.symbolMap[it] as ParameterSymbol } }

        override val returnType: Type = declaringClass.type

        override val simpleName: String by lazy { ctx.ZEN_CONSTRUCTOR().text }

        override val type: FunctionType by lazy { FunctionType(returnType, parameters.map { it.type }) }

        override val cst: ConstructorDeclarationContext = ctx

        override val unit: CompilationUnit = unit

        override val textRange: TextRange by lazy { ctx.textRange }

        override val simpleNameTextRange: TextRange by lazy { ctx.ZEN_CONSTRUCTOR().textRange }

        override fun toString(): String = simpleName
    })
}
