package raylras.intellizen.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class RangeTest {

    @ParameterizedTest
    @MethodSource("contains")
    void contains(TextRange a, TextRange b, boolean expected) {
        System.out.printf("test: %s contains %s, expected: %s%n", a, b, expected);
        Assertions.assertEquals(expected, TextRangeKt.contains(a, b));
    }

    static Stream<Arguments> contains() {
        return Stream.of(
                // single line
                Arguments.of(new TextRange(0, 1, 0, 3), new TextRange(0, 0, 0, 1), false),
                Arguments.of(new TextRange(0, 1, 0, 3), new TextRange(0, 1, 0, 2), true),
                Arguments.of(new TextRange(0, 1, 0, 3), new TextRange(0, 2, 0, 3), true),
                Arguments.of(new TextRange(0, 1, 0, 3), new TextRange(0, 3, 0, 4), false),
                // multi lines
                Arguments.of(new TextRange(0, 2, 2, 0), new TextRange(0, 0, 2, 0), false),
                Arguments.of(new TextRange(0, 2, 2, 0), new TextRange(0, 2, 0, 3), true),
                Arguments.of(new TextRange(0, 2, 2, 0), new TextRange(0, 2, 1, 3), true),
                Arguments.of(new TextRange(0, 2, 2, 0), new TextRange(0, 2, 2, 0), true),
                Arguments.of(new TextRange(0, 2, 2, 0), new TextRange(0, 2, 2, 3), false),
                Arguments.of(new TextRange(0, 2, 2, 0), new TextRange(2, 0, 2, 3), false)
        );
    }

}