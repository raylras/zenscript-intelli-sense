package raylras.zen.util;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser;

public class DebugUtils {

    public static String prettyPrintTree(ParseTree node) {
        StringBuilder builder = new StringBuilder();
        prettyPrintTree(builder, "", node, true);
        return builder.toString();
    }

    private static void prettyPrintTree(StringBuilder builder, String indent, ParseTree node, boolean last) {
        if (node == null) {
            return;
        }

        String text;
        if (node instanceof TerminalNode) {
            Token token = ((TerminalNode) node).getSymbol();
            int tokenType = token.getType();
            if (tokenType == -1) {
                text = "<EOF>";
            } else {
                text = ZenScriptLexer.ruleNames[tokenType - 1] + ":" + token.getText();
            }
        } else if (node instanceof RuleContext){
            int ruleIndex = ((RuleContext) node).getRuleIndex();
            text = ZenScriptParser.ruleNames[ruleIndex];
        } else {
            text = null;
        }

        builder.append(indent).append(last ? "\\-- " : "|-- ").append(text).append("\n");

        int childCount = node.getChildCount();
        for (int i = 0; i < childCount; i++) {
            prettyPrintTree(builder, indent + (last ? "    " : "|   "), node.getChild(i), i == childCount - 1);
        }
    }

}
