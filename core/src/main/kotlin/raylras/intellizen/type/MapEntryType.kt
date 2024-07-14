
package raylras.intellizen.type

import raylras.intellizen.CompilationEnvironment
import raylras.intellizen.symbol.Symbol
import raylras.intellizen.symbol.SymbolProvider
import raylras.intellizen.symbol.symbolSequence

data class MapEntryType(val keyType: Type, val valueType: Type) : Type, SymbolProvider {
    override val typeName by lazy { "Map.Entry<${keyType.typeName},${valueType.typeName}>" }

    override val simpleTypeName by lazy { "Map.Entry<${keyType.simpleTypeName},${valueType.simpleTypeName}>" }

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        return symbolSequence {
            variable("key", keyType)
            variable("value", valueType)
        }
    }

    override fun toString() = typeName
}
