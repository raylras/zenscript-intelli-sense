package raylras.intellizen.type

import raylras.intellizen.CompilationEnvironment
import raylras.intellizen.symbol.Operator
import raylras.intellizen.symbol.Symbol
import raylras.intellizen.symbol.SymbolProvider
import raylras.intellizen.symbol.symbolSequence

interface NumberType : Type, SymbolProvider {
    override fun isCastableTo(that: Type?, env: CompilationEnvironment): Boolean {
        return when (that) {
            is NumberType, is StringType -> {
                true
            }

            else -> super.isCastableTo(that, env)
        }
    }

    override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
        val builtin = symbolSequence {
            operator(Operator.NEG, this@NumberType)
            operator(Operator.ADD, this@NumberType) {
                parameter("value", this@NumberType)
            }
            operator(Operator.SUB, this@NumberType) {
                parameter("value", this@NumberType)
            }
            operator(Operator.MUL, this@NumberType) {
                parameter("value", this@NumberType)
            }
            operator(Operator.DIV, this@NumberType) {
                parameter("value", this@NumberType)
            }
            operator(Operator.MOD, this@NumberType) {
                parameter("value", this@NumberType)
            }
            operator(Operator.CONCAT, StringType) {
                parameter("value", StringType)
            }
            operator(Operator.EQUALS, BoolType) {
                parameter("value", this@NumberType)
            }
            operator(Operator.NOT_EQUALS, BoolType) {
                parameter("value", this@NumberType)
            }
            operator(Operator.LESS, BoolType) {
                parameter("value", this@NumberType)
            }
            operator(Operator.LESS_EQUALS, BoolType) {
                parameter("value", this@NumberType)
            }
            operator(Operator.GREATER, BoolType) {
                parameter("value", this@NumberType)
            }
            operator(Operator.GREATER_EQUALS, BoolType) {
                parameter("value", this@NumberType)
            }
        }
        val classDeclared = env?.classes
            ?.firstOrNull { it.qualifiedName == IntType.typeName }
            ?.getSymbols()
            ?: emptySequence()
        return builtin + classDeclared + getExpands(env)
    }
}
