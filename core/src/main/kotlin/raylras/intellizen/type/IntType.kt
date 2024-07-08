package raylras.intellizen.type

import raylras.intellizen.CompilationEnvironment
import raylras.intellizen.symbol.Operator
import raylras.intellizen.symbol.Symbol
import raylras.intellizen.symbol.symbolSequence

object IntType : NumberType {
    override val typeName = "int"

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        return symbolSequence {
            operator(Operator.RANGE, IntRangeType) {
                parameter("value", IntType)
            }
        } + super.getSymbols(env)
    }

    override fun toString() = typeName
}
