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

    private static void prettyPrintTree(StringBuilder builder, String prefix, ParseTree n, boolean isLast) {
        if (n != null) {
            String display;
            if (n instanceof TerminalNode) {
                Token token = ((TerminalNode) n).getSymbol();
                int index = token.getType();
                if (index == -1) {
                    display = "<EOF>";
                } else {
                    display = ZenScriptLexer.ruleNames[index - 1] + ":" + token.getText();
                }
            } else {
                int index = ((RuleContext) n).getRuleIndex();
                display = ZenScriptParser.ruleNames[index];
            }

            builder.append(prefix).append(isLast ? "\\-- " : "|-- ").append(display).append("\n");

            int childCount = n.getChildCount();
            for (int i = 0; i < childCount; i++) {
                prettyPrintTree(builder, prefix + (isLast ? "    " : "|   "), n.getChild(i), i == childCount - 1);
            }
        }
    }
}
