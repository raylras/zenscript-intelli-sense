package raylras.zen.model.type

import raylras.zen.model.symbol.Executable
import raylras.zen.model.symbol.Operator
import raylras.zen.model.symbol.OperatorFunctionSymbol
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.symbol.impl.createOperatorFunctionSymbol

class MemberValidator {
    private val properties = HashMap<String, Symbol>()
    private val executables = HashMap<ExecutableData, Symbol>()
    private val casterTypes = ArrayList<Type>()

    fun add(symbol: Symbol) {
        when (symbol) {
            is Executable -> {
                when {
                    symbol is OperatorFunctionSymbol && symbol.operator == Operator.AS -> {
                        addCaster(symbol)
                    }
                    else -> {
                        addExecutable(symbol)
                    }
                }
            }

            else -> {
                addProperty(symbol)
            }
        }
    }

    fun getMembers(): Collection<Symbol> {
        val members = ArrayList<Symbol>(properties.size + executables.size + 1)
        members.addAll(properties.values)
        members.addAll(executables.values)
        when {
            casterTypes.size == 1 -> {
                members.add(createOperatorFunctionSymbol(Operator.AS, casterTypes[0]))
            }
            casterTypes.size > 1 -> {
                members.add(createOperatorFunctionSymbol(Operator.AS, IntersectionType(casterTypes)))
            }
        }
        return members
    }

    private fun addProperty(symbol: Symbol) {
        properties.putIfAbsent(symbol.simpleName, symbol)
    }

    private fun <T> addExecutable(symbol: T) where T : Symbol, T : Executable {
        val parameterTypes = symbol.parameters.map { it.type }.toList()
        executables.putIfAbsent(
            ExecutableData(symbol.simpleName, parameterTypes),
            symbol
        )
    }

    private fun addCaster(caster: OperatorFunctionSymbol) {
        when (val returnType = caster.returnType) {
            is IntersectionType -> {
                casterTypes.addAll(returnType.typeList)
            }

            else -> {
                casterTypes.add(returnType)
            }
        }
    }

    private data class ExecutableData(val name: String, val parameters: List<Type>)
}
