package raylras.zen.model.type

import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.symbol.Operator
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.symbol.SymbolProvider
import raylras.zen.model.symbol.symbolSequence

object BoolType : Type, SymbolProvider {
    override val typeName = "bool"

    override fun isCastableTo(that: Type?, env: CompilationEnvironment): Boolean {
        return when (that) {
            is StringType -> true
            else -> super.isCastableTo(that, env)
        }
    }

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        val builtin = symbolSequence {
            operator(Operator.NOT, BoolType)
            operator(Operator.BITWISE_AND, BoolType) {
                parameter("value", BoolType)
            }
            operator(Operator.BITWISE_OR, BoolType) {
                parameter("value", BoolType)
            }
            operator(Operator.LOGICAL_AND, BoolType) {
                parameter("value", BoolType)
            }
            operator(Operator.LOGICAL_OR, BoolType) {
                parameter("value", BoolType)
            }
            operator(Operator.XOR, BoolType) {
                parameter("value", BoolType)
            }
            operator(Operator.CONCAT, StringType) {
                parameter("value", StringType)
            }
            operator(Operator.EQUALS, BoolType) {
                parameter("value", BoolType)
            }
            operator(Operator.NOT_EQUALS, BoolType) {
                parameter("value", BoolType)
            }
            operator(Operator.LESS, BoolType) {
                parameter("value", BoolType)
            }
            operator(Operator.LESS_EQUALS, BoolType) {
                parameter("value", BoolType)
            }
            operator(Operator.GREATER, BoolType) {
                parameter("value", BoolType)
            }
            operator(Operator.GREATER_EQUALS, BoolType) {
                parameter("value", BoolType)
            }
        }
        val classDeclared = env?.classes
            ?.filter { it.qualifiedName == typeName }
            ?.flatMap { it.getSymbols() }
            ?: emptySequence()
        return builtin + classDeclared + getExpands(env)
    }

    override fun toString() = typeName
}
