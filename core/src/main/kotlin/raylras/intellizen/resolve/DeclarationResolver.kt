package raylras.intellizen.resolve

import org.antlr.v4.runtime.tree.ParseTree
import raylras.intellizen.CompilationUnit
import raylras.intellizen.Visitor
import raylras.intellizen.parser.ZenScriptParser.*
import raylras.intellizen.scope.Scope
import raylras.intellizen.symbol.ClassSymbol
import raylras.intellizen.symbol.Symbol
import raylras.intellizen.symbol.impl.*
import raylras.intellizen.util.ArrayStack
import raylras.intellizen.util.Stack

fun CompilationUnit.resolveDeclarations() {
    this.accept(DeclarationVisitor(this))
}

private class DeclarationVisitor(private val unit: CompilationUnit) : Visitor<Unit>() {
    val scopeStack: Stack<Scope> = ArrayStack()
    val classStack: Stack<ClassSymbol> = ArrayStack()

    fun enterScope(cst: ParseTree, callback: (Scope) -> Unit = {}) {
        val scope = Scope(scopeStack.peek(), cst)
        unit.scopeMap[cst] = scope
        scopeStack.push(scope)
        callback(scope)
        scopeStack.pop()
    }

    fun enterClass(symbol: ClassSymbol, callback: () -> Unit) {
        classStack.push(symbol)
        callback()
        classStack.pop()
    }

    fun currentClass(): ClassSymbol? {
        return classStack.peek()
    }

    fun putSymbol(cst: ParseTree, symbol: Symbol) {
        unit.symbolMap[cst] = symbol
        scopeStack.peek()?.symbols?.add(symbol)
    }

    override fun visitCompilationUnit(ctx: CompilationUnitContext) {
        enterScope(ctx) {
            visitChildren(ctx)
        }
    }

    override fun visitImportDeclaration(ctx: ImportDeclarationContext) {
        createImportSymbol(ctx, unit) {
            putSymbol(ctx, it)
        }
    }

    override fun visitFunctionDeclaration(ctx: FunctionDeclarationContext) {
        createFunctionSymbol(ctx.simpleName(), ctx, unit) {
            putSymbol(ctx, it)
            enterScope(ctx.functionBody() ?: ctx) {
                visitChildren(ctx)
            }
        }
    }

    override fun visitExpandFunctionDeclaration(ctx: ExpandFunctionDeclarationContext) {
        createExpandFunctionSymbol(ctx.simpleName(), ctx, unit) { symbol ->
            putSymbol(ctx, symbol)
            enterScope(ctx.functionBody() ?: ctx) { scope ->
                scope.symbols.add(createThisSymbol { symbol.expandingType })
                visitChildren(ctx)
            }
        }
    }

    override fun visitFormalParameter(ctx: FormalParameterContext) {
        createParameterSymbol(ctx.simpleName(), ctx, unit) {
            putSymbol(ctx, it)
            visitChildren(ctx)
        }
    }

    override fun visitClassDeclaration(ctx: ClassDeclarationContext) {
        createClassSymbol(ctx, unit) { symbol ->
            putSymbol(ctx, symbol)
            enterClass(symbol) {
                enterScope(ctx) { scope ->
                    scope.symbols.add(createThisSymbol { symbol.type })
                    visitChildren(ctx)
                }
            }
        }
    }

    override fun visitConstructorDeclaration(ctx: ConstructorDeclarationContext) {
        createConstructorSymbol(ctx, unit, currentClass()) {
            putSymbol(ctx, it)
            enterScope(ctx.constructorBody() ?: ctx) {
                visitChildren(ctx)
            }
        }
    }

    override fun visitVariableDeclaration(ctx: VariableDeclarationContext) {
        createVariableSymbol(ctx.simpleName(),ctx.typeLiteral(), ctx, unit) {
            putSymbol(ctx, it)
            visitChildren(ctx)
        }
    }

    override fun visitOperatorFunctionDeclaration(ctx: OperatorFunctionDeclarationContext) {
        createOperatorFunctionSymbol(ctx.operator(), ctx, unit) {
            putSymbol(ctx, it)
            enterScope(ctx) {
                visitChildren(ctx)
            }
        }
    }

    override fun visitForeachStatement(ctx: ForeachStatementContext) {
        enterScope(ctx.foreachBody() ?: ctx) {
            visitChildren(ctx)
        }
    }

    override fun visitForeachVariable(ctx: ForeachVariableContext) {
        createVariableSymbol(ctx.simpleName(), null, ctx, unit) {
            putSymbol(ctx, it)
        }
    }

    override fun visitWhileStatement(ctx: WhileStatementContext) {
        enterScope(ctx.statement() ?: ctx) {
            visitChildren(ctx)
        }
    }

    override fun visitFunctionExpr(ctx: FunctionExprContext) {
        enterScope(ctx.functionBody() ?: ctx) {
            visitChildren(ctx)
        }
    }
}
