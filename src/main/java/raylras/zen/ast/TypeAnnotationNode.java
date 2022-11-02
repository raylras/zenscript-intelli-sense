package raylras.zen.ast;

public class TypeAnnotationNode extends ASTNode {

    public TypeAnnotationNode() {
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
