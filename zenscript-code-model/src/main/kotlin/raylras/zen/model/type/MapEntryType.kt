
package raylras.zen.model.type

import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.symbol.SymbolProvider
import raylras.zen.model.symbol.symbolSequence

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
