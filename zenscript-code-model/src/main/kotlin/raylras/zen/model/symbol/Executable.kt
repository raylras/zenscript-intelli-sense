package raylras.zen.model.symbol

import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.SemanticEntity
import raylras.zen.model.type.Type

interface Executable : SemanticEntity {
    val parameters: List<ParameterSymbol>
    val returnType: Type
}

fun Sequence<Executable>.filterOverloads(actualArgTypes: List<Type>): Sequence<Executable> {
    return this.filter { it.matchesArgumentTypes(actualArgTypes) }
}

fun Executable.matchesArgumentTypes(actualArgTypes: List<Type>, env: CompilationEnvironment? = null): Boolean {
    val actualArgSize = actualArgTypes.size
    val formalParamSize = parameters.size
    val lastParam = parameters.lastOrNull()
    when {
        actualArgSize < formalParamSize -> {
            val restParamsMatched = lastParam?.isVararg == true
                    || parameters.asSequence().drop(actualArgSize).all { it.isOptional }
            if (!restParamsMatched) {
                return false
            }
        }

        actualArgSize > formalParamSize -> {
            val restArgsMatched = lastParam?.isVararg == true
                    && actualArgTypes.asSequence().drop(formalParamSize).all { it.isCastableTo(lastParam.type, env) }
            if (!restArgsMatched) {
                return false
            }
        }
    }
    return (parameters zip actualArgTypes).all { (param, arg) ->
        arg.isCastableTo(param.type, env)
    }
}
