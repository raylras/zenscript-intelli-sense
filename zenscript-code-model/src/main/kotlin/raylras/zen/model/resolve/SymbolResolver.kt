package raylras.zen.model.resolve

import org.antlr.v4.runtime.tree.ParseTree
import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Visitor
import raylras.zen.model.lookupScope
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.symbol.*
import raylras.zen.util.contains

fun lookupSymbol(cst: ParseTree?, unit: CompilationUnit): Collection<Symbol> {
    cst ?: return emptyList()
    val expr = findRootExpression(cst) ?: return emptyList()
    val visitor = SymbolVisitor(unit, cst)
    expr.accept(visitor)
    return visitor.result
}

fun lookupClass(cst: QualifiedNameContext, unit: CompilationUnit): Collection<ClassSymbol> {
    val visitor = SymbolVisitor(unit, cst)
    cst.accept(visitor)
    val classes: MutableCollection<ClassSymbol> = ArrayList()
    for (symbol in visitor.result) {
        when (symbol) {
            is ClassSymbol -> {
                classes.add(symbol)
            }

            is ImportSymbol -> {
                symbol.targets
                    .filterIsInstance<ClassSymbol>()
                    .forEach { classes.add(it) }
            }
        }
    }
    return classes
}

private class SymbolVisitor(val unit: CompilationUnit, val cst: ParseTree) : Visitor<SymbolProvider>() {
    var result: Collection<Symbol> = emptyList()

    override fun visitQualifiedName(ctx: QualifiedNameContext): SymbolProvider {
        val simpleNames: List<SimpleNameContext> = ctx.simpleName()
        if (simpleNames.isEmpty()) {
            return SymbolProvider.EMPTY
        }

        val start: SimpleNameContext = simpleNames.first()
        var symbols = lookupSymbol(start, start.getText(), unit)
        updateResult(symbols)
        for (i in 1 until simpleNames.size) {
            val simpleName = simpleNames[i]
            symbols = filterMember(symbols, simpleName.getText(), unit.env)
            if (simpleName in cst) {
                updateResult(symbols)
            }
        }
        return SymbolProvider.EMPTY
    }

    override fun visitSimpleNameExpr(ctx: SimpleNameExprContext): SymbolProvider {
        val symbols = lookupSymbol(ctx, ctx.simpleName().getText(), unit)
        if (ctx.simpleName() in cst) {
            updateResult(symbols)
        }
        return SymbolProvider.EMPTY
    }

    override fun visitMemberAccessExpr(ctx: MemberAccessExprContext): SymbolProvider {
        val provider = visit(ctx.expression()).getSymbols(unit.env).toList()
        if (provider.size != 1) {
            return SymbolProvider.EMPTY
        }

        if (ctx.simpleName() in cst) {
            val symbol = provider.first()
            val type = symbol.type
            val symbols = when {
                symbol is ClassSymbol -> {
                    symbol.getSymbols(unit.env)
                        .filter { it is Modifiable && it.isStatic }
                        .filter { it.simpleName == ctx.simpleName().getText() }
                        .toList()
                }
                type is SymbolProvider -> {
                    type.getSymbols(unit.env)
                        .filter { it.simpleName == ctx.simpleName().text }
                        .toList()
                }
                else -> {
                    emptyList()
                }
            }
            updateResult(symbols)
        }
        return SymbolProvider.EMPTY
    }

    override fun defaultResult(): SymbolProvider {
        return SymbolProvider.EMPTY
    }

    private fun updateResult(result: Collection<Symbol>) {
        this.result = result
    }
}

private fun filterMember(provider: Collection<Symbol>, memberName: String, env: CompilationEnvironment): Collection<Symbol> {
    if (provider.size != 1) {
        return emptyList()
    }
    return provider
        .asSequence()
        .filterIsInstance<SymbolProvider>()
        .flatMap { it.getSymbols(env) }
        .filter { it.simpleName == memberName}
        .toList()
}

private fun lookupSymbol(cst: ParseTree?, name: String, unit: CompilationUnit): Collection<Symbol> {
    var result = lookupLocalSymbol(cst, name, unit)
    if (result.isNotEmpty()) {
        return result
    }

    result = lookupToplevelSymbol(name, unit)
    if (result.isNotEmpty()) {
        return result
    }

    result = lookupImportSymbol(name, unit)
    if (result.isNotEmpty()) {
        return result
    }

    result = lookupGlobalSymbol(name, unit.env)
    if (result.isNotEmpty()) {
        return result
    }

    result = lookupPackageSymbol(name, unit.env)
    if (result.isNotEmpty()) {
        return result
    }

    return emptyList()
}

private fun lookupLocalSymbol(cst: ParseTree?, name: String, unit: CompilationUnit): Collection<Symbol> {
    return lookupScope(cst, unit)
        ?.symbols
        ?.filter { it.simpleName == name }
        ?: emptyList()
}

private fun lookupToplevelSymbol(name: String, unit: CompilationUnit): Collection<Symbol> {
    return unit.topLevelSymbols
        .filter { it.simpleName == name }
        .toList()
}

private fun lookupImportSymbol(name: String, unit: CompilationUnit): Collection<ImportSymbol> {
    return unit.imports
        .filter { it.simpleName == name }
}

private fun lookupGlobalSymbol(name: String, env: CompilationEnvironment): Collection<Symbol> {
    return env.globals
        .filter { it.simpleName == name }
        .toList()
}

private fun lookupPackageSymbol(name: String, env: CompilationEnvironment): Collection<PackageSymbol> {
    return env.rootPackage.subpackages
        .filter { it.simpleName == name }
        .toList()
}

private fun findRootExpression(cst: ParseTree?): ParseTree? {
    var current: ParseTree? = cst
    while (current != null && current.parent != null) {
        val parent = current.parent
        if (parent::class in ROOT_EXPRESSION_PARENTS) {
            return current
        }
        current = parent
    }
    return null
}

private val ROOT_EXPRESSION_PARENTS = setOf(
    ImportDeclarationContext::class,
    ForeachStatementContext::class,
    ForeachVariableContext::class,
    WhileStatementContext::class,
    IfStatementContext::class,
    ExpressionStatementContext::class,
    ReturnStatementContext::class
)
