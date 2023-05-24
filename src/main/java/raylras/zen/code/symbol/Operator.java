package raylras.zen.code.symbol;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Operator {
    ADD, SUB, MUL, DIV, MOD, CAT, OR, AND, XOR, NEG, NOT,
    INDEX_SET, INDEX_GET, RANGE, CONTAINS, COMPARE, MEMBER_GETTER, MEMBER_SETTER, EQUALS,
    ITERABLE, CASTER, NOT_OPERATOR;

    private static final Map<String, Operator> mappings =
        Arrays.stream(Operator.values())
            .collect(Collectors.toMap(Enum::name, Function.identity()));

    public static Operator fromString(String op) {
        return mappings.getOrDefault(op, Operator.NOT_OPERATOR);
    }

}