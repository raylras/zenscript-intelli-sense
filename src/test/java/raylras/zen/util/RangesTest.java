package raylras.zen.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RangesTest {

    @MethodSource
    @ParameterizedTest
    void isRangeContainsPosition(Range range, int line, int column, boolean condition) {
        System.out.printf("Range(%d:%d-%d:%d) contains Position(%d:%d) should be %s%n", range.startLine, range.startColumn, range.endLine, range.endColumn, line, column, condition);
        assertEquals(condition, Ranges.isRangeContainsPosition(range, line, column));
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