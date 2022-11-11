package raylras.zen.ast;

public abstract class ASTNode implements Node {

    protected ASTNode parent;
    protected NodeLocation location;

    public ASTNode getParent() {
        return parent;
    }

    public void setParent(ASTNode parent) {
        this.parent = parent;
    }

    public NodeLocation getLocation() {
        return location;
    }

    public void setLocation(NodeLocation location) {
        this.location = location;
    }

    public abstract <T> T accept(ASTNodeVisitor<? extends T> visitor);

    public abstract void addChild(ASTNode node);

}
