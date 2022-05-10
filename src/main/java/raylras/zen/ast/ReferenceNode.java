package raylras.zen.ast;

public abstract class ReferenceNode extends ASTNode {

    private final String reference;

    public ReferenceNode(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    @Override
    public String toString() {
        return reference;
    }

}
