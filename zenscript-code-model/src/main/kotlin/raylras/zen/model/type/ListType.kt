package raylras.zen.model.type

import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.symbol.Modifiable.Modifier
import raylras.zen.model.symbol.Operator
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.symbol.SymbolProvider
import raylras.zen.model.symbol.symbolSequence

data class ListType(val elementType: Type) : Type, SymbolProvider {
    override val typeName: String by lazy { "[${elementType.typeName}]" }

    override val simpleTypeName by lazy { "[${elementType.simpleTypeName}]" }

    override fun isCastableTo(that: Type?, env: CompilationEnvironment): Boolean {
        return when (that) {
            is ListType -> {
                elementType.isCastableTo(that.elementType, env)
            }

            is ArrayType -> {
                elementType.isCastableTo(that.elementType, env)
            }

            else -> super.isCastableTo(that, env)
        }
    }

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        return symbolSequence {
            variable("length", IntType, Modifier.IMPLICIT_VAL)
            function("remove", VoidType) {
                parameter("index", IntType)
            }
            operator(Operator.INDEX_GET, elementType) {
                parameter("index", IntType)
            }
            operator(Operator.INDEX_SET, elementType) {
                parameter("index", IntType)
                parameter("element", elementType)
            }
            operator(Operator.ADD, this@ListType) {
                parameter("element", elementType)
            }
            operator(Operator.FOR_IN, this@ListType)
        }
    }

    override fun toString() = typeName
}
