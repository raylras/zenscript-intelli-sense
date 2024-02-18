package raylras.zen.model.symbol

import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.type.Type
import raylras.zen.model.type.test

enum class Operator(val literal: String, val kind: Kind) {
    // unary
    NEG("-", Kind.UNARY),
    NOT("!", Kind.UNARY),
    AS("as", Kind.UNARY),
    FOR_IN("for_in", Kind.UNARY),

    // binary
    ADD("+", Kind.BINARY),
    SUB("-", Kind.BINARY),
    MUL("*", Kind.BINARY),
    DIV("/", Kind.BINARY),
    MOD("%", Kind.BINARY),
    CONCAT("~", Kind.BINARY),
    BITWISE_OR("|", Kind.BINARY),
    BITWISE_AND("&", Kind.BINARY),
    LOGICAL_OR("||", Kind.BINARY),
    LOGICAL_AND("&&", Kind.BINARY),
    XOR("^", Kind.BINARY),
    INDEX_GET("[]", Kind.BINARY),
    RANGE("..", Kind.BINARY),
    HAS("has", Kind.BINARY),
    MEMBER_GET(".", Kind.BINARY),
    EQUALS("==", Kind.BINARY),
    NOT_EQUALS("!=", Kind.BINARY),
    LESS("<", Kind.BINARY),
    LESS_EQUALS("<=", Kind.BINARY),
    GREATER(">", Kind.BINARY),
    GREATER_EQUALS(">=", Kind.BINARY),

    // trinary
    MEMBER_SET(".=", Kind.TRINARY),
    INDEX_SET("[]=", Kind.TRINARY),

    ERROR("ERROR", Kind.ERROR);

    init {
        kind.operators[literal] = this
    }

    enum class Kind {
        UNARY, BINARY, TRINARY, ERROR;
        val operators: MutableMap<String, Operator> = HashMap()
    }

    companion object {
        fun of(literal: String?, kind: Kind): Operator {
            return kind.operators[literal] ?: ERROR
        }

        fun of(literal: String?, paramSize: Int): Operator {
            return of(literal, when (paramSize) {
                0 -> Kind.UNARY
                1 -> Kind.BINARY
                2 -> Kind.TRINARY
                else -> Kind.ERROR
            })
        }
    }
}

fun Type.filterOperator(op: Operator, env: CompilationEnvironment): Sequence<OperatorFunctionSymbol> {
    return when (this) {
        is SymbolProvider -> {
            this.getSymbols(env)
                .filter { it is OperatorFunctionSymbol }
                .map { it as OperatorFunctionSymbol }
                .filter { it.operator == op }
        }

        else -> {
            emptySequence()
        }
    }
}

fun Type.applyUnaryOperator(op: Operator, env: CompilationEnvironment): Type? {
    return filterOperator(op, env)
        .firstOrNull()
        ?.returnType
}

fun Type.applyBinaryOperator(op: Operator, t1: Type?, env: CompilationEnvironment): Type? {
    return filterOperator(op, env)
        .maxByOrNull { t1.test(it.parameters[0].type, env) }
        ?.returnType
}

fun Type.applyTernaryOperator(op: Operator, t1: Type?, t2: Type?, env: CompilationEnvironment): Type? {
    return filterOperator(op, env)
        .maxByOrNull {
            Math.min(
                t1.test(it.parameters[0].type, env).intValue,
                t2.test(it.parameters[1].type, env).intValue
            )
        }
        ?.returnType
}

fun Type.hasCasterFor(target: Type, env: CompilationEnvironment): Boolean {
    return this.applyUnaryOperator(Operator.AS, env)?.isSupertypeTo(target) ?: false
}
