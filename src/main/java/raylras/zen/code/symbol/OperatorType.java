package raylras.zen.code.symbol;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum OperatorType {
    ADD, SUB, MUL, DIV, MOD, CAT, OR, AND, XOR, NEG, NOT,
    INDEXSET, INDEXGET, RANGE, CONTAINS, COMPARE, MEMBERGETTER, MEMBERSETTER, EQUALS,
    ITERABLE, CASTER, NOT_OPERATOR;


    private static final Map<String, OperatorType> mappings =
        Arrays.stream(OperatorType.values())
            .collect(Collectors.toMap(Enum::name, Function.identity()));

    public static OperatorType fromString(String op) {
        return mappings.getOrDefault(op, OperatorType.NOT_OPERATOR);
    }
}