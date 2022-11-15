package raylras.zen.ast;

import raylras.zen.ast.type.Node;

public abstract class ASTNode implements Node {

    protected ASTNode parent;
    protected TextRange textRange;

    public ASTNode getParent() {
        return parent;
    }

    public void setParent(ASTNode parent) {
        this.parent = parent;
    }

    public TextRange getTextRange() {
        return textRange;
    }

    public void setTextRange(TextRange textRange) {
        this.textRange = textRange;
    }

    public abstract void addChild(ASTNode node);

    public abstract <T> T accept(ASTNodeVisitor<? extends T> visitor);

}
