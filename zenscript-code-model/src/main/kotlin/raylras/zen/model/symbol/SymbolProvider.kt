package raylras.zen.model.symbol

import raylras.zen.model.CompilationEnvironment

interface SymbolProvider {
    fun getSymbols(env: CompilationEnvironment? = null): Sequence<Symbol>

    object EMPTY: SymbolProvider {
        override fun getSymbols(env: CompilationEnvironment?) = emptySequence<Symbol>()
    }
}
