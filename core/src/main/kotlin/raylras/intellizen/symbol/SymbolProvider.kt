package raylras.intellizen.symbol

import raylras.intellizen.CompilationEnvironment

interface SymbolProvider {
    fun getSymbols(env: CompilationEnvironment? = null): Sequence<Symbol>

    object EMPTY: SymbolProvider {
        override fun getSymbols(env: CompilationEnvironment?) = emptySequence<Symbol>()
    }
}
