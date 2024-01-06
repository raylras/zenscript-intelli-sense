package raylras.zen.model.type

import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.symbol.SymbolProvider

object StringType : Type, SymbolProvider {
    override val typeName = "string"

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        val classDeclared = env?.classes
            ?.filter { it.qualifiedName == typeName }
            ?.flatMap { it.getSymbols() }
            ?: emptySequence()
        return classDeclared + getExpands(env)
    }

    override fun toString() = typeName
}
