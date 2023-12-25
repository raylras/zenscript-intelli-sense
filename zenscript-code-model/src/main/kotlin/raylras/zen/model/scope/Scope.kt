package raylras.zen.model.scope

import org.antlr.v4.runtime.tree.ParseTree
import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.symbol.SymbolProvider

class Scope(val parent: Scope?, val cst: ParseTree) : SymbolProvider {
    val symbols = ArrayList<Symbol>()

    fun firstOrNull(predicate: (Symbol) -> Boolean): Symbol? {
        var scope: Scope? = this
        while (scope != null) {
            for (symbol in scope.getSymbols()) if (predicate(symbol)) return symbol
            scope = scope.parent
        }
        return null
    }

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        return symbols.asSequence()
    }
}
