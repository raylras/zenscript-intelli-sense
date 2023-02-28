package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.TreeNode;

/**
 * Abstract base class of AST nodes that represent expressions.
 */
public abstract class Expression extends TreeNode {

    public Expression(Range range) {
        super(range);
    }

}
