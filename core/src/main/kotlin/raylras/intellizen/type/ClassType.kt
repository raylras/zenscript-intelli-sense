package raylras.intellizen.type

import raylras.intellizen.CompilationEnvironment
import raylras.intellizen.symbol.*

data class ClassType(val symbol: ClassSymbol) : Type, SymbolProvider {
    override val typeName: String by symbol::qualifiedName
    override val simpleTypeName: String by symbol::simpleName
    val interfaces: Sequence<ClassType> by symbol::interfaces

    override fun isSupertypeTo(type: Type): Boolean {
        if (type is ClassType) {
            val deque = ArrayDeque<ClassType>().apply {
                addFirst(type)
            }
            while (deque.isNotEmpty()) {
                val pop = deque.removeFirst()
                if (pop == this) {
                    return true
                }
                deque.addAll(pop.interfaces)
            }
        }
        return false
    }

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        val validator = MemberValidator()
        symbol.declaredMembers.filter { it.isStatic.not() }.forEach { validator.add(it) }
        val interfaces = symbol.interfaces.toCollection(ArrayDeque())
        while (interfaces.isNotEmpty()) {
            val pop = interfaces.removeFirst()
            pop.getSymbols(env).forEach { validator.add(it) }
            interfaces.addAll(pop.interfaces)
        }
        return validator.getMembers().asSequence()
    }

    override fun toString() = typeName
}

fun ClassType?.isFunctionalInterface(): Boolean {
    this?.symbol?.declaredMembers?.count()?.equals(1) ?: return false
    firstAnonymousFunctionOrNull() ?: return false
    return true
}

fun ClassType?.firstAnonymousFunctionOrNull(): FunctionSymbol? {
    return this?.symbol?.declaredMembers
        ?.filterIsInstance<FunctionSymbol>()
        ?.firstOrNull { it.simpleName.isEmpty() }
}
