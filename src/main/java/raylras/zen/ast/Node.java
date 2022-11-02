package raylras.zen.ast;

public interface Node {
    NodeLocation getLocation();

    void setLocation(NodeLocation location);

    <T> T accept(ASTNodeVisitor<? extends T> visitor);
}
