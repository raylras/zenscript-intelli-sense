package raylras.zen.ast;

public class IdentifierNode extends ASTNode {

    private final String name;

    public IdentifierNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitIdentifier(this);
    }

    @Override
    public String toString() {
        return name;
    }

}
