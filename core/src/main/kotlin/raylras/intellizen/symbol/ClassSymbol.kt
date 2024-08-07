package raylras.intellizen.symbol

import raylras.intellizen.type.ClassType

interface ClassSymbol : Symbol, SymbolProvider {
    val qualifiedName: String

    val declaredMembers: Sequence<Symbol>

    val interfaces: Sequence<ClassType>

    override val type: ClassType
}
