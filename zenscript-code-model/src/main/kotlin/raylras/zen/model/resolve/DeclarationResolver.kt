package raylras.zen.model.resolve

import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Visitor
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.scope.Scope
import raylras.zen.model.symbol.ClassSymbol
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.symbol.impl.*
import raylras.zen.util.ArrayStack
import raylras.zen.util.Stack

fun CompilationUnit.resolveDeclarations() {
    this.accept(DeclarationVisitor(this))
}

private class DeclarationVisitor(private val unit: CompilationUnit) : Visitor<Unit>() {
    val scopeStack: Stack<Scope> = ArrayStack()
    val classStack: Stack<ClassSymbol> = ArrayStack()

    fun enterScope(cst: RuleNode, also: (Scope) -> Unit = {}) {
        val scope = Scope(scopeStack.peek(), cst)
        unit.scopeMap[cst] = scope
        scopeStack.push(scope)
        also.invoke(scope)
        visitChildren(cst)
        scopeStack.pop()
    }

    fun enterClass(symbol: ClassSymbol, also: () -> Unit) {
        classStack.push(symbol)
        also.invoke()
        classStack.pop()
    }

    fun currentClass(): ClassSymbol? {
        return classStack.peek()
    }

    fun enterSymbol(cst: ParseTree, symbol: Symbol) {
        unit.symbolMap[cst] = symbol
        scopeStack.peek()?.symbols?.add(symbol)
    }

    override fun visitCompilationUnit(ctx: CompilationUnitContext) {
        enterScope(ctx)
    }

    override fun visitImportDeclaration(ctx: ImportDeclarationContext) {
        val simpleNameCtx = ctx.alias ?: ctx.qualifiedName()?.simpleName()?.last()
        createImportSymbol(simpleNameCtx, ctx, unit) {
            unit.imports.add(it)
        }
    }

    override fun visitFunctionDeclaration(ctx: FunctionDeclarationContext) {
        createFunctionSymbol(ctx.simpleName(), ctx, unit) {
            enterSymbol(ctx, it)
            enterScope(ctx)
        }
    }

    override fun visitExpandFunctionDeclaration(ctx: ExpandFunctionDeclarationContext) {
        createExpandFunctionSymbol(ctx.simpleName(), ctx, unit) {
            enterSymbol(ctx, it)
            enterScope(ctx) { scope ->
                scope.symbols.add(createThisSymbol { it.expandingType })
            }
        }
    }

    override fun visitFormalParameter(ctx: FormalParameterContext) {
        createParameterSymbol(ctx.simpleName(), ctx, unit) {
            enterSymbol(ctx, it)
        }
    }

    override fun visitFunctionBody(ctx: FunctionBodyContext) {
        enterScope(ctx)
    }

    override fun visitClassDeclaration(ctx: ClassDeclarationContext) {
        val simpleNameCtx = ctx.simpleName() ?: ctx.simpleNameOrPrimitiveType()
        createClassSymbol(simpleNameCtx, ctx, unit) { symbol ->
            enterSymbol(ctx, symbol)
            enterClass(symbol) {
                enterScope(ctx) { scope ->
                    scope.symbols.add(createThisSymbol { symbol.type })
                }
            }
        }
    }

    override fun visitConstructorDeclaration(ctx: ConstructorDeclarationContext) {
        createConstructorSymbol(ctx, unit, currentClass()) {
            enterSymbol(ctx, it)
            enterScope(ctx)
        }
    }

    override fun visitVariableDeclaration(ctx: VariableDeclarationContext) {
        createVariableSymbol(ctx.simpleName(),ctx.typeLiteral(), ctx, unit) {
            enterSymbol(ctx, it)
        }
    }

    override fun visitOperatorFunctionDeclaration(ctx: OperatorFunctionDeclarationContext) {
        createOperatorFunctionSymbol(ctx.operator(), ctx, unit) {
            enterSymbol(ctx, it)
            enterScope(ctx)
        }
    }

    override fun visitForeachStatement(ctx: ForeachStatementContext) {
        enterScope(ctx)
    }

    override fun visitForeachVariable(ctx: ForeachVariableContext) {
        createVariableSymbol(ctx.simpleName(), null, ctx, unit) {
            enterSymbol(ctx, it)
        }
    }

    override fun visitWhileStatement(ctx: WhileStatementContext) {
        enterScope(ctx)
    }

    override fun visitFunctionExpr(ctx: FunctionExprContext) {
        enterScope(ctx)
    }
}
