package raylras.zen.util;

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

}
