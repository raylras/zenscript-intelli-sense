package raylras.zen.util;

import org.eclipse.lsp4j.Position;
import stanhebben.zenscript.ZenParsedFile;
import stanhebben.zenscript.util.ZenPosition;

public class PosUtil {

    private PosUtil() {}

    // ZenScript's ZenPosition counts from 1, LSP4J‘s Position counts from 0
    // so Position = ZenPosition - 1

    public static Position convert(ZenPosition zenPos) {
        return new Position(zenPos.getLine() - 1, zenPos.getLineOffset() - 1);
    }

    public static ZenPosition convert(ZenParsedFile file, String fileNameFallBack, Position pos) {
        return new ZenPosition(file, pos.getLine() + 1, pos.getCharacter() + 1, fileNameFallBack);
    }

}
