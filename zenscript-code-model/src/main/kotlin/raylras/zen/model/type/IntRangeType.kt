package raylras.zen.model.type

import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.symbol.Operator
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.symbol.SymbolProvider
import raylras.zen.model.symbol.symbolSequence

object IntRangeType : Type, SymbolProvider {
    override val typeName = "stanhebben.zenscript.value.IntRange"

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        return symbolSequence {
            variable("from", IntType)
            variable("to", IntType)
            operator(Operator.FOR_IN, ListType(IntType))
        }
    }

    override fun toString() = typeName
}
