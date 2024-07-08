package raylras.intellizen.type

import raylras.intellizen.CompilationEnvironment
import raylras.intellizen.symbol.Modifiable.Modifier
import raylras.intellizen.symbol.Operator
import raylras.intellizen.symbol.Symbol
import raylras.intellizen.symbol.SymbolProvider
import raylras.intellizen.symbol.symbolSequence

data class ArrayType(val elementType: Type) : Type, SymbolProvider {
    override val typeName by lazy { elementType.typeName + "[]" }

    override val simpleTypeName by lazy { elementType.simpleTypeName + "[]" }

    override fun isCastableTo(that: Type?, env: CompilationEnvironment): Boolean {
        return when (that) {
            is ArrayType -> {
                elementType.isCastableTo(that.elementType, env)
            }

            is ListType -> {
                elementType.isCastableTo(that.elementType, env)
            }

            is ClassType -> {
                that.typeName == "crafttweaker.data.IData"
            }

            else -> super.isCastableTo(that, env)
        }
    }

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        return symbolSequence {
            variable("length", IntType, Modifier.IMPLICIT_VAL)
            operator(Operator.INDEX_GET, elementType) {
                parameter("index", IntType)
            }
            operator(Operator.INDEX_SET, elementType) {
                parameter("index", IntType)
                parameter("value", elementType)
            }
            operator(Operator.ADD, this@ArrayType) {
                parameter("value", elementType)
            }
            operator(Operator.FOR_IN, ListType(elementType))
        }
    }

    override fun toString() = typeName
}
