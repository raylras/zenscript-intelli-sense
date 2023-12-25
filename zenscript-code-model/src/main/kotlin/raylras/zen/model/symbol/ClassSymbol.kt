package raylras.zen.model.symbol

import raylras.zen.model.type.ClassType

interface ClassSymbol : Symbol, SymbolProvider {
    val qualifiedName: String

    val declaredMembers: Sequence<Symbol>

    val interfaces: Sequence<ClassSymbol>

    override val type: ClassType
}
