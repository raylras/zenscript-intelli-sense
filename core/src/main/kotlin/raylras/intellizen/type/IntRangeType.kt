package raylras.intellizen.type

import raylras.intellizen.CompilationEnvironment
import raylras.intellizen.symbol.Operator
import raylras.intellizen.symbol.Symbol
import raylras.intellizen.symbol.SymbolProvider
import raylras.intellizen.symbol.symbolSequence

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
