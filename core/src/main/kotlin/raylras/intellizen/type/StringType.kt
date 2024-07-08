package raylras.intellizen.type

import raylras.intellizen.CompilationEnvironment
import raylras.intellizen.symbol.*

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
