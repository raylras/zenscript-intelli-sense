package raylras.zen.model.type

import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.symbol.ClassSymbol
import raylras.zen.model.symbol.FunctionSymbol
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.symbol.SymbolProvider
import java.util.*

data class ClassType(val symbol: ClassSymbol) : Type, SymbolProvider {
    override val typeName: String by symbol::qualifiedName
    override val simpleTypeName: String by symbol::simpleName

    override fun isSupertypeTo(type: Type): Boolean {
        if (type is ClassType) {
            val deque: Deque<ClassSymbol> = ArrayDeque()
            deque.push(type.symbol)
            while (deque.isNotEmpty()) {
                val pop = deque.pop()
                if (pop == this.symbol) {
                    return true
                }
                deque.addAll(pop.interfaces)
            }
        }
        return false
    }

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        val validator = MemberValidator()
        symbol.declaredMembers.forEach { validator.add(it) }
        val interfaceDeque: Deque<ClassSymbol> = ArrayDeque(symbol.interfaces.toList())
        while (interfaceDeque.isNotEmpty()) {
            val pop = interfaceDeque.pop()
            pop.getSymbols().forEach { validator.add(it) }
            interfaceDeque.addAll(pop.interfaces)
        }
        return validator.getMembers().asSequence()
    }

    override fun toString() = typeName
}

fun ClassType?.firstAnonymousFunctionOrNull(): FunctionSymbol? {
    return this?.symbol?.declaredMembers
        ?.filterIsInstance<FunctionSymbol>()
        ?.firstOrNull { it.simpleName.isEmpty() }
}
