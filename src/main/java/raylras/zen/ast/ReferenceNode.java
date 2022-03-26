package raylras.zen.ast;

public class ReferenceNode extends ASTNode {

    private String reference;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public void accept(ASTVisitor<?> visitor) {
        visitor.visitReference(this);
    }

    @Override
    public String toString() {
        return reference;
    }

}
