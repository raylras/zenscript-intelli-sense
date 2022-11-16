package raylras.zen.ast;

import raylras.zen.ast.type.Node;

import java.util.List;

public abstract class ASTNode implements Node {

    protected ASTNode parent;
    protected TextRange textRange;

    public ASTNode getParent() {
        return parent;
    }

    public void setParent(ASTNode parent) {
        this.parent = parent;
    }

    @Override
    public TextRange getTextRange() {
        return textRange;
    }

    public void setTextRange(TextRange textRange) {
        this.textRange = textRange;
    }

    public abstract void addChild(ASTNode node);

    public abstract List<Node> getChildren();

    public abstract <T> T accept(ASTNodeVisitor<? extends T> visitor);

}
