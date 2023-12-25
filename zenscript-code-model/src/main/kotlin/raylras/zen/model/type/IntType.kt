package raylras.zen.model.type

import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.symbol.Operator
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.symbol.symbolSequence

object IntType : NumberType {
    override val typeName = "int"

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        val builtin = symbolSequence {
            operator(Operator.RANGE, IntRangeType) {
                parameter("value", IntType)
            }
        }
        val classDeclared = env?.classes
            ?.firstOrNull { it.qualifiedName == typeName }
            ?.getSymbols()
            ?: emptySequence()
        return builtin + classDeclared + getExpands(env)
    }

    override fun toString() = typeName
}
