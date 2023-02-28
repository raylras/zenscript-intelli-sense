package raylras.zen.code;

import org.eclipse.lsp4j.Position;

public class Ranges {

    public static org.eclipse.lsp4j.Range toLSPRange(Range range) {
        return new org.eclipse.lsp4j.Range(new Position(range.startLine, range.startColumn), new Position(range.endLine, range.endColumn));
    }

    public static boolean isRangeContainsPosition(Range range, int line, int column) {
        if (line < range.startLine || range.endLine < line) {
            return false;
        }
        if (line == range.endLine) {
            return range.startColumn <= column && column <= range.endColumn;
        }
        return true;
    }

}
