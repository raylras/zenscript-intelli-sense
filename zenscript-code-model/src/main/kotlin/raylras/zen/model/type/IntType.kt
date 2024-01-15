package raylras.zen.model.type

import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.symbol.Operator
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.symbol.symbolSequence

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
