package raylras.zen.code.tree.stmt;

import raylras.zen.code.Range;
import raylras.zen.code.tree.TreeNode;

/**
 * Abstract base class of AST nodes that represent statements.
 */
public abstract class Statement extends TreeNode {

    public Statement(Range range) {
        super(range);
    }

}
