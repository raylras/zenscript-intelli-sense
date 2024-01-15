package raylras.zen.model.type

import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.symbol.Operator
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.symbol.SymbolProvider
import raylras.zen.model.symbol.symbolSequence

object StringType : Type, SymbolProvider {
    override val typeName = "string"

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        val builtIn = symbolSequence {
            operator(Operator.CONCAT, StringType) {
                parameter("other", AnyType)
            }
        }
        val classDeclared = env?.classes
            ?.filter { it.qualifiedName == typeName }
            ?.flatMap { it.getSymbols() }
            ?: emptySequence()
        return builtIn + classDeclared + getExpands(env)
    }

    override fun toString() = typeName
}
