package raylras.zen.util;

import org.antlr.v4.runtime.Token;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import raylras.zen.ast.ASTNode;

public class PosUtils {

    private PosUtils() {}

    // Line:
    // LSP4J‘s Line counts from 0, ANTLR4's Line counts from 1
    // LSP_Line == ANTLR_Line - 1

    // Column:
    // LSP4J‘s Column counts from 0, ANTLR4's Column counts from 0
    // LSP_Column == ANTLR_Column

    public static Position toLSPPos(Token token) {
        return new Position(token.getLine() - 1, token.getCharPositionInLine());
    }

    public static Position toLSPPos(ASTNode node) {
        return new Position(node.getLine() - 1, node.getColumn());
    }

    public static Range toLSPRange(Token token) {
        return new Range(toLSPPos(token), new Position(token.getLine() - 1, getLength(token)));
    }

    public static Range toLSPRange(ASTNode node) {
        return new Range(toLSPPos(node), new Position(node.getLastLine() - 1, node.getLastColumn()));
    }

    public static int getLength(Token token) {
        return token.getText().length();
    }

    public static int getLength(ASTNode node) {
        return node.getLastColumn() - node.getColumn();
    }

}
