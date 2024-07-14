package raylras.intellizen.symbol.impl

import raylras.intellizen.CompilationUnit
import raylras.intellizen.parser.ZenScriptParser.ConstructorDeclarationContext
import raylras.intellizen.symbol.ClassSymbol
import raylras.intellizen.symbol.ConstructorSymbol
import raylras.intellizen.symbol.ParameterSymbol
import raylras.intellizen.symbol.ParseTreeLocatable
import raylras.intellizen.type.FunctionType
import raylras.intellizen.type.Type
import raylras.intellizen.util.TextRange
import raylras.intellizen.util.textRange

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
