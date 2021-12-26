package raylras.zen.util;

import org.antlr.v4.runtime.Token;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import stanhebben.zenscript.ZenParsedFile;
import stanhebben.zenscript.util.ZenPosition;

public class PosUtil {

    private PosUtil() {}

    // Line:
    // LSP4J‘s Line counts from 0, ZenScript's Line counts from 1, ANTLR4's Line counts from 1
    // so LSP_Line == Zen_Line - 1 == ANTLR_Line - 1

    // Column:
    // LSP4J‘s Column counts from 0, ZenScript's Column counts from 1, ANTLR4's Column counts from 0
    // so LSP_Column == Zen_Column - 1 == ANTLR_Column

    public static Position convert(ZenPosition zenPos) {
        return new Position(zenPos.getLine() - 1, zenPos.getLineOffset() - 1);
    }

    public static ZenPosition transform(ZenParsedFile file, String fileNameFallBack, Position pos) {
        return new ZenPosition(file, pos.getLine() + 1, pos.getCharacter() + 1, fileNameFallBack);
    }

    public static Position getPosition(Token token) {
        return new Position(token.getLine() - 1, token.getCharPositionInLine());
    }

    public static Range getRange(Token token) {
        return new Range(getPosition(token), new Position(token.getLine() - 1, getLength(token)));
    }

    public static int getLength(Token token) {
        return token.getText().length();
    }

}
