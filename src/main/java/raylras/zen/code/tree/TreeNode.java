package raylras.zen.code.tree;

import raylras.zen.code.Range;

/**
 * Base class of all Abstract Syntax Tree (AST) nodes.
 * An AST node represents a source code construct, such as a name,
 * type, expression, statement, or declaration.
 */
public abstract class TreeNode {

    public Range range;

    public TreeNode(Range range) {
        this.range = range;
    }

    public abstract void accept(TreeVisitor visitor);

    public final void acceptChild(TreeVisitor visitor, TreeNode child) {
        if (child != null) {
            child.accept(visitor);
        }
    }

    public final void acceptChildren(TreeVisitor visitor, Iterable<? extends TreeNode> children) {
        for (TreeNode child : children) {
            child.accept(visitor);
        }
    }

}
