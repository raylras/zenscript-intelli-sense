package raylras.zen.model.type

import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.symbol.*

object StringType : Type, SymbolProvider {
    override val typeName = "string"

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        val builtIn = symbolSequence {
            operator(Operator.CONCAT, StringType) {
                parameter("other", AnyType)
            }
        }
        val classDeclared =
            env?.lookupClass(typeName)?.declaredMembers?.filter { it.isStatic.not() }.orEmpty()
        return builtIn + classDeclared + getExpands(env)
    }

    override fun toString() = typeName
}
