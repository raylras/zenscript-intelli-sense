package raylras.zen.util;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

public class Ranges {

    /**
     * @deprecated use {@link Range#contains(Position)}
     */
    @Deprecated
    public static boolean isRangeContainsLineAndColumn(Range range, int line, int column) {
        Position pos = Position.of(line, column);
        return range.contains(new Range(pos, pos));
    }

    public static boolean contains(ParseTree a, ParseTree b) {
        return Range.of(a).contains(Range.of(b));
    }

    public static boolean contains(Token token, ParseTree cst) {
        return Range.of(token).contains(Range.of(cst));
    }

    public static org.eclipse.lsp4j.Range toLspRange(ParseTree cst) {
        return Range.of(cst).toLspRange();
    }

}
