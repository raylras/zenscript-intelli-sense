package raylras.zen.util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import raylras.zen.ast.TextRange;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class CommonUtils {

    private CommonUtils() {
    }

    public static Path toPath(String uri) {
        return Paths.get(URI.create(uri));
    }

    public static TextRange getTextRange(ParserRuleContext ctx) {
        int startLine = ctx.start.getLine();
        int startColumn = ctx.start.getCharPositionInLine();
        int endLine = ctx.stop.getLine();
        int endColumn = ctx.stop.getCharPositionInLine();
        return new TextRange(startLine, startColumn, endLine, endColumn);
    }

    public static TextRange getTextRange(Token token) {
        int startLine = token.getLine();
        int startColumn = token.getCharPositionInLine();
        int endColumn = startColumn + token.getText().length();
        return new TextRange(startLine, startColumn, startLine, endColumn);
    }

}
