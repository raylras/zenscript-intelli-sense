package raylras.zen.model.symbol

import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.SemanticEntity
import raylras.zen.model.type.Type
import raylras.zen.model.type.TypeTest
import raylras.zen.model.type.test

interface Executable : SemanticEntity {
    val parameters: List<ParameterSymbol>
    val returnType: Type
}

fun Sequence<Executable>.filterOverloads(
    actualArgTypes: List<Type>,
    env: CompilationEnvironment? = null
): Sequence<Executable> {
    val pairs = mapNotNull { e -> e.distanceTo(actualArgTypes, env)?.let { d -> Pair(e, d) } }
    val minDistance = pairs.map { it.second }.minOrNull() ?: return emptySequence()
    return pairs.filter { it.second == minDistance }.map { it.first }
}

/**
 * Returns the non-negative distance between the executable and the actual argument types, null if mismatched.
 */
private fun Executable.distanceTo(actualArgTypes: List<Type>, env: CompilationEnvironment?): Int? {
    val actualArgSize = actualArgTypes.size
    val formalParamSize = parameters.size
    val lastParam = parameters.lastOrNull()
    val maxDistance = parameters.zip(actualArgTypes) { p, a -> p.distanceTo(a, env) ?: return null }.maxOrNull() ?: 0
    return when {
        actualArgSize < formalParamSize -> {
            val restParamsMatched = parameters.drop(actualArgSize).all { it.isOptional || it.isVararg }
            if (restParamsMatched) {
                maxDistance + 1
            } else {
                null
            }
        }

        actualArgSize > formalParamSize -> {
            val restArgsMatched = lastParam?.isVararg == true
                    && actualArgTypes.asSequence().drop(formalParamSize).all { it.isCastableTo(lastParam.type, env) }
            if (restArgsMatched) {
                maxDistance + 1
            } else {
                null
            }
        }

        else -> maxDistance
    }
}

/**
 * Returns the non-negative distance between the parameter and the actual argument type, null if mismatched.
 */
private fun ParameterSymbol.distanceTo(actualArgType: Type, env: CompilationEnvironment?): Int? {
    return with(actualArgType.test(type, env)) {
        when (this) {
            TypeTest.MISMATCH -> null

            TypeTest.EXACT -> when {
                isOptional -> intValue + 1
                isVararg -> intValue + 1
                else -> intValue
            }

            TypeTest.IMPLICIT -> when {
                isOptional -> intValue + 2
                isVararg -> intValue + 2
                else -> intValue + 1
            }
        }
    }
}
