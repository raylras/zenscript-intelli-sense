package raylras.zen.model.resolve

import org.antlr.v4.runtime.tree.ParseTree
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Visitor
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.symbol.ParameterSymbol
import raylras.zen.model.symbol.Symbol

fun getParameters(cst: ParseTree?, unit: CompilationUnit): List<ParameterSymbol> {
    return cst?.accept(FormalParameterVisitor(unit)) ?: emptyList()
}

private class FormalParameterVisitor(val unit: CompilationUnit) : Visitor<List<ParameterSymbol>>() {
    override fun visitFunctionDeclaration(ctx: FunctionDeclarationContext): List<ParameterSymbol> {
        return mapToSymbol(ctx.formalParameter())
    }

    override fun visitExpandFunctionDeclaration(ctx: ExpandFunctionDeclarationContext): List<ParameterSymbol> {
        return mapToSymbol(ctx.formalParameter())
    }

    override fun visitConstructorDeclaration(ctx: ConstructorDeclarationContext): List<ParameterSymbol> {
        return mapToSymbol(ctx.formalParameter())
    }

    override fun visitOperatorFunctionDeclaration(ctx: OperatorFunctionDeclarationContext): List<ParameterSymbol> {
        return mapToSymbol(ctx.formalParameter())
    }

    private inline fun <reified T: Symbol> mapToSymbol(ctxList: List<*>): List<T> {
        return ctxList.map { unit.symbolMap[it] as T }
    }
}
