package raylras.intellizen.scope

import org.antlr.v4.runtime.tree.ParseTree
import raylras.intellizen.CompilationEnvironment
import raylras.intellizen.symbol.Symbol
import raylras.intellizen.symbol.SymbolProvider

class Scope(val parent: Scope?, val cst: ParseTree) : SymbolProvider {
    val symbols = ArrayList<Symbol>()

    fun filter(predicate: (Symbol) -> Boolean): Sequence<Symbol> {
        return generateSequence(this) { it.parent }
            .flatMap { it.getSymbols() }
            .filter(predicate)
    }

    fun firstOrNull(predicate: (Symbol) -> Boolean): Symbol? {
        return filter { predicate(it) }.firstOrNull(predicate)
    }

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        return symbols.asSequence()
    }
}
