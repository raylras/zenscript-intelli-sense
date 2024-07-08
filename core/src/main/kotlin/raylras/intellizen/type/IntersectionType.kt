package raylras.intellizen.type

import raylras.intellizen.CompilationEnvironment
import raylras.intellizen.symbol.Symbol
import raylras.intellizen.symbol.SymbolProvider

data class IntersectionType(val typeList: List<Type>) : Type, SymbolProvider {
    override val typeName: String by lazy {
        typeList.joinToString(" & ") { it.typeName }
    }

    override val simpleTypeName: String by lazy {
        typeList.joinToString(" & ") { it.simpleTypeName }
    }

    override fun isSupertypeTo(type: Type): Boolean {
        return typeList.any { it.isSupertypeTo(type) }
    }

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        val validator = MemberValidator()
        for (type in typeList) {
            if (type is SymbolProvider) {
                type.getSymbols().forEach { validator.add(it) }
            }
        }
        return validator.getMembers().asSequence()
    }

    override fun toString() = typeName
}
