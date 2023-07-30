package raylras.zen.util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayDeque;
import java.util.Queue;

public class CSTNodes {

    public static ParseTree getCstAtLineAndColumn(ParseTree root, int line, int column) {
        Queue<ParseTree> queue = new ArrayDeque<>();
        queue.add(root);
        ParseTree found = null;
        while (!queue.isEmpty()) {
            ParseTree node = queue.poll();
            Range range = Ranges.of(node);
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
        Token prevToken = getPrevToken(tokenStream, node);
        if (prevToken == null) {
            return null;
        }
        ParseTree root = getRoot(node);
        Range cursor = Ranges.of(prevToken);
        ParseTree prevNode = getCstAtLineAndColumn(root, cursor.endLine, cursor.endColumn);
        return (prevNode instanceof TerminalNode) ? (TerminalNode) prevNode : null;
    }

    public static Token getPrevToken(TokenStream tokenStream, ParseTree node) {
        int i = getStartTokenIndex(node) - 1;
        while (i >= 0) {
            Token token = tokenStream.get(i);
            if (token.getChannel() == Token.DEFAULT_CHANNEL) {
                return token;
            }
            i--;
        }
        return null;
    }

    public static Token getNextToken(TokenStream tokenStream, ParseTree node) {
        int i = getStopTokenIndex(node) + 1;
        while (i <= tokenStream.size()) {
            Token token = tokenStream.get(i);
            if (token.getChannel() == Token.DEFAULT_CHANNEL) {
                return token;
            }
            i++;
        }
        return null;
    }

    private static int getStartTokenIndex(ParseTree node) {
        if (node instanceof TerminalNode) {
            return ((TerminalNode) node).getSymbol().getTokenIndex();
        }
        if (node instanceof ParserRuleContext) {
            return ((ParserRuleContext) node).getStart().getTokenIndex();
        }
        return -1;
    }

    private static int getStopTokenIndex(ParseTree node) {
        if (node instanceof TerminalNode) {
            return ((TerminalNode) node).getSymbol().getTokenIndex();
        }
        if (node instanceof ParserRuleContext) {
            return ((ParserRuleContext) node).getStop().getTokenIndex();
        }
        return -1;
    }

    private static ParseTree getRoot(ParseTree node) {
        ParseTree root = node;
        while (root != null && root.getParent() != null) {
            root = root.getParent();
        }
        return root;
    }

}
