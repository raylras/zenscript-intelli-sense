package raylras.zen.ast;

public class TypeNameNode extends ASTNode {

    private final String value;

    public TypeNameNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void addChild(ASTNode node) {
    }

    @Override
    public String toString() {
        return value;
    }

}
