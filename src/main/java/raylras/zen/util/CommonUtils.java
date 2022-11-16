package raylras.zen.util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import raylras.zen.ast.TextRange;
import raylras.zen.ast.type.Node;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Node> toChildrenList(Object... children) {
        List<Node> childrenList = new ArrayList<>();
        for (Object child : children) {
            if (child instanceof Node) {
                childrenList.add((Node) child);
            } else if (child instanceof List<?>) {
                for (Object o : ((List<?>) child)) {
                    if (o instanceof Node) {
                        childrenList.add((Node) o);
                    }
                }
            }
        }
        return childrenList;
    }

}
