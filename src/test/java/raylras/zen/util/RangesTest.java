package raylras.zen.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RangesTest {

    @ParameterizedTest
    @MethodSource("isRangeContainsPosition")
    void isRangeContainsPosition(Range range, int line, int column, boolean expected) {
        System.out.printf("test: %s contains (%d:%d), expected: %s%n", range, line, column, expected);
        assertEquals(expected, Ranges.isRangeContainsLineAndColumn(range, line, column));
    }

    static Stream<Arguments> isRangeContainsPosition() {
        return Stream.of(
                Arguments.of(new Range(1,1,1,1), 1,1, true),
                Arguments.of(new Range(0,0,1,1), 0,0, true),
                Arguments.of(new Range(0,0,1,1), 1,1, true),
                Arguments.of(new Range(0,0,2,2), 1,1, true),
                Arguments.of(new Range(0,0,2,2), 0,3, true),
                Arguments.of(new Range(0,0,2,2), 2,3, false),
                Arguments.of(new Range(1,1,2,2), 0,0, false),
                Arguments.of(new Range(1,1,2,2), 3,1, false),
                Arguments.of(new Range(3,0,3,4), 3,27, false),
                Arguments.of(new Range(1,0,1,48), 6,12, false)
        );
    }

}