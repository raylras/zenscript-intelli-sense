package raylras.zen.util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.Node;
import raylras.zen.ast.Position;
import raylras.zen.ast.Range;

public final class PosUtils {

    // LSP4J position starts from (0:0). ANTLR4 position starts from (1:0)
    // (0:0) means line:0, column:0

    private PosUtils() {}

    @Nullable
    public static Position toASTPosition(org.eclipse.lsp4j.Position pos) {
        if (pos == null) return null;
        return new Position(pos.getLine() + 1, pos.getCharacter());
    }

    public static Range toASTRange(org.eclipse.lsp4j.Range range) {
        return new Range(range.getStart().getLine() + 1, range.getStart().getCharacter(), range.getEnd().getLine() + 1, range.getEnd().getCharacter());
    }

    public static org.eclipse.lsp4j.Range toLSPRange(Range range) {
        return new org.eclipse.lsp4j.Range(new org.eclipse.lsp4j.Position(range.getLine() - 1, range.getColumn()), new org.eclipse.lsp4j.Position(range.getLastLine() - 1, range.getLastColumn()));
    }

    public static Position makeASTPosition(ParserRuleContext ctx) {
        int line = ctx.start.getLine();
        int column = ctx.start.getCharPositionInLine();
        return new Position(line, column);
    }

    public static Range makeASTRange(ParserRuleContext ctx) {
        int line = ctx.start.getLine();
        int column = ctx.start.getCharPositionInLine();
        int lastLine = ctx.stop.getLine();
        int lastColumn = ctx.stop.getCharPositionInLine() + ctx.stop.getText().length();
        return new Range(line, column, lastLine, lastColumn);
    }

    public static org.eclipse.lsp4j.Range makeLSPRange(Token token) {
        org.eclipse.lsp4j.Position start = makeLSPPos(token);
        org.eclipse.lsp4j.Position end = new org.eclipse.lsp4j.Position(token.getLine() - 1, getLength(token));
        return new org.eclipse.lsp4j.Range(start, end);
    }

    public static org.eclipse.lsp4j.Range makeLSPRange(Node node) {
        return toLSPRange(node.getRange());
    }

    public static org.eclipse.lsp4j.Position makeLSPPos(Token token) {
        return new org.eclipse.lsp4j.Position(token.getLine() - 1, token.getCharPositionInLine());
    }

    public static org.eclipse.lsp4j.Position makeLSPPos(Node node) {
        return new org.eclipse.lsp4j.Position(node.getRange().getLine() - 1, node.getRange().getColumn());
    }

    public static int getLength(Token token) {
        return token.getText().length();
    }

    public static int getLength(Node node) {
        return node.getRange().getLastColumn() - node.getRange().getColumn();
    }

}
