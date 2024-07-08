package raylras.intellizen.symbol

import raylras.intellizen.symbol.Modifiable.Modifier
import raylras.intellizen.symbol.impl.createFunctionSymbol
import raylras.intellizen.symbol.impl.createOperatorFunctionSymbol
import raylras.intellizen.symbol.impl.createParameterSymbol
import raylras.intellizen.symbol.impl.createVariableSymbol
import raylras.intellizen.type.Type

fun symbolSequence(action: SymbolBuilder.() -> Unit): Sequence<Symbol> {
    val builder = SymbolBuilder()
    builder.action()
    return builder.symbols.asSequence()
}

class SymbolBuilder {
    val symbols = ArrayList<Symbol>()
    fun variable(simpleName: String, type: Type, modifier: Modifier = Modifier.IMPLICIT_VAL): SymbolBuilder {
        symbols.add(createVariableSymbol(simpleName, type, modifier))
        return this
    }

    fun function(simpleName: String, returnType: Type, action: ParameterBuilder.() -> Unit = {}): SymbolBuilder {
        val builder = ParameterBuilder()
        builder.action()
        symbols.add(createFunctionSymbol(simpleName, returnType, builder.parameters))
        return this
    }

    fun operator(operator: Operator, returnType: Type, action: ParameterBuilder.() -> Unit = {}): SymbolBuilder {
        val builder = ParameterBuilder()
        builder.action()
        symbols.add(createOperatorFunctionSymbol(operator, returnType, builder.parameters))
        return this
    }
}

class ParameterBuilder {
    val parameters = ArrayList<ParameterSymbol>()
    fun parameter(
        simpleName: String,
        type: Type,
        optional: Boolean = false,
        vararg: Boolean = false
    ): ParameterBuilder {
        parameters.add(createParameterSymbol(simpleName, type, optional, vararg))
        return this
    }
}
