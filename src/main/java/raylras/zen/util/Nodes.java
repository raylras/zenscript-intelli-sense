package raylras.zen.util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayDeque;
import java.util.Queue;

public class Nodes {

    public static ParseTree getNodeAtLineAndColumn(ParseTree root, int line, int column) {
        Queue<ParseTree> queue = new ArrayDeque<>();
        queue.add(root);
        ParseTree found = null;
        while (!queue.isEmpty()) {
            ParseTree node = queue.poll();
            Range range = Ranges.from(node);
            if (Ranges.isRangeContainsLineAndColumn(range, line, column)) {
                found = node;
                queue.clear();
                for (int i = 0; i < node.getChildCount(); i++) {
                    queue.add(node.getChild(i));
                }
            }
        }
        return found;
    }

    public static TerminalNode getPrevTerminal(TokenStream tokenStream, ParseTree node) {
        ParseTree root = getRoot(node);
        Token prevToken = getPrevToken(node, tokenStream);
        if (prevToken == null)
            return null;
        Range cursor = Ranges.from(prevToken);
        ParseTree prevNode = getNodeAtLineAndColumn(root, cursor.endLine, cursor.endColumn);
        return (prevNode instanceof TerminalNode) ? (TerminalNode) prevNode : null;
    }

    private static Token getPrevToken(ParseTree node, TokenStream tokenStream) {
        int i = getStartTokenIndex(node) - 1;
        while (i >= 0) {
            Token token = tokenStream.get(i);
            if (token.getChannel() == Token.DEFAULT_CHANNEL)
                return token;
            i--;
        }
        return null;
    }

    private static ParseTree getRoot(ParseTree node) {
        ParseTree root = node;
        while (root != null && root.getParent() != null) {
            root = root.getParent();
        }
        return root;
    }

    private static int getStartTokenIndex(ParseTree node) {
        if (node instanceof TerminalNode)
            return ((TerminalNode) node).getSymbol().getTokenIndex();
        if (node instanceof ParserRuleContext)
            return ((ParserRuleContext) node).getStart().getTokenIndex();
        return -1;
    }

}
