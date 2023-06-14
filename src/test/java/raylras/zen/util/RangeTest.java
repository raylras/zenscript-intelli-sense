package raylras.zen.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class RangeTest {

    @ParameterizedTest
    @MethodSource("contains")
    void contains(Range a, Range b, boolean expected) {
        System.out.printf("test: %s contains %s, expected: %s%n", a, b, expected);
        Assertions.assertEquals(expected, a.contains(b));
    }

    static Stream<Arguments> contains() {
        return Stream.of(
                // single line
                Arguments.of(new Range(0, 1, 0, 3), new Range(0, 0, 0, 1), false),
                Arguments.of(new Range(0, 1, 0, 3), new Range(0, 1, 0, 2), true),
                Arguments.of(new Range(0, 1, 0, 3), new Range(0, 2, 0, 3), true),
                Arguments.of(new Range(0, 1, 0, 3), new Range(0, 3, 0, 4), false),
                // multi lines
                Arguments.of(new Range(0, 2, 2, 0), new Range(0, 0, 2, 0), false),
                Arguments.of(new Range(0, 2, 2, 0), new Range(0, 2, 0, 3), true),
                Arguments.of(new Range(0, 2, 2, 0), new Range(0, 2, 1, 3), true),
                Arguments.of(new Range(0, 2, 2, 0), new Range(0, 2, 2, 0), true),
                Arguments.of(new Range(0, 2, 2, 0), new Range(0, 2, 2, 3), false),
                Arguments.of(new Range(0, 2, 2, 0), new Range(2, 0, 2, 3), false)
        );
    }

}