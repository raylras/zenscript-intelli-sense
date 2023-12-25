package raylras.zen.model.type

import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.symbol.Operator
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.symbol.SymbolProvider
import raylras.zen.model.symbol.symbolSequence

data class MapType(val keyType: Type, val valueType: Type) : Type, SymbolProvider {
    override val typeName by lazy { "${valueType.typeName}[${keyType.typeName}]" }

    override val simpleTypeName by lazy { "${valueType.simpleTypeName}[${keyType.simpleTypeName}]" }

    override fun isCastableTo(that: Type?, env: CompilationEnvironment): Boolean {
        return when (that) {
            is MapType -> {
                (keyType.isCastableTo(that.keyType, env)
                        && valueType.isCastableTo(that.valueType, env))
            }

            is ClassType -> {
                that.typeName == "crafttweaker.data.IData"
            }

            else -> super.isCastableTo(that, env)
        }
    }

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        return symbolSequence {
            variable("length", IntType)
            variable("keys", ArrayType(keyType))
            variable("keySet", ArrayType(keyType))
            variable("values", ArrayType(valueType))
            variable("valueSet", ArrayType(valueType))
            variable("entrySet", ArrayType(MapEntryType(keyType, valueType)))
            operator(Operator.INDEX_GET, valueType) {
                parameter("key", keyType)
            }
            operator(Operator.INDEX_SET, VoidType) {
                parameter("key", keyType)
                parameter("value", valueType)
            }
            operator(Operator.MEMBER_GET, valueType) {
                parameter("key", keyType)
            }
            operator(Operator.MEMBER_SET, VoidType) {
                parameter("key", keyType)
                parameter("value", valueType)
            }
            operator(Operator.FOR_IN, this@MapType)
        }
    }

    override fun toString() = typeName
}
