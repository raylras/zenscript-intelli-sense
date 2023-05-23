package raylras.zen.util;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayDeque;
import java.util.Queue;

public class Nodes {

    public static ParseTree getNodeAtPosition(ParseTree start, int line, int column) {
        Queue<ParseTree> queue = new ArrayDeque<>();
        queue.add(start);
        ParseTree found = null;
        while (!queue.isEmpty()) {
            ParseTree node = queue.poll();
            Range range = Ranges.from(node);
            if (Ranges.isRangeContainsPosition(range, line, column)) {
                found = node;
                queue.clear();
                for (int i = 0; i < node.getChildCount(); i++) {
                    queue.add(node.getChild(i));
                }
            }
        }
        return found;
    }


    public static ParseTree getNextSiblingNode(RuleContext node) {
        if (node == null || node.getParent() == null) {
            return null;
        }

        int childCount = node.getParent().getChildCount();
        RuleContext parent = node.getParent();
        for (int i = 0; i < childCount - 1; i++) {
            if (parent.getChild(i) == node) {
                return parent.getChild(i + 1);
            }
        }
        return null;
    }

    public static ParseTree getNextNode(RuleContext node) {
        if (node == null) {
            return null;
        }

        ParseTree siblingNode = getNextSiblingNode(node);
        if (siblingNode == null) {
            return getNextNode(node.getParent());
        }
        return siblingNode;

    }


}
