package raylras.zen.util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class CSTNodes {

    public static ParseTree getCstAtLineAndColumn(ParseTree root, int line, int column) {
        Deque<ParseTree> deque = getCstStackAtLineAndColumn(root, line, column);
        return deque.peekFirst();
    }

    public static Deque<ParseTree> getCstStackAtLineAndColumn(ParseTree root, int line, int column) {
        Queue<ParseTree> tempQueue = new ArrayDeque<>();
        tempQueue.add(root);
        Deque<ParseTree> result = new ArrayDeque<>();
        while (!tempQueue.isEmpty()) {
            ParseTree cst = tempQueue.poll();
            Range range = Ranges.of(cst);
            if (Ranges.isRangeContainsLineAndColumn(range, line, column)) {
                result.addFirst(cst);
                tempQueue.clear();
                for (int i = 0; i < cst.getChildCount(); i++) {
                    tempQueue.add(cst.getChild(i));
                }
            }
        }
        return result;
    }

    public static int getTokenType(Token token) {
        if (token != null) {
            return token.getType();
        } else {
            return Token.INVALID_TYPE;
        }
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

    public static String getText(ParseTree cst) {
        if (cst != null) {
            return cst.getText();
        } else {
            return "";
        }
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
