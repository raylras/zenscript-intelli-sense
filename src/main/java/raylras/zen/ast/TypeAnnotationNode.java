package raylras.zen.ast;

import raylras.zen.ast.type.TypeAnnotation;
import raylras.zen.ast.type.TypeName;

public class TypeAnnotationNode extends ASTNode implements TypeAnnotation {

    private TypeName typeName;

    public TypeAnnotationNode() {
    }

    public TypeName getTypeName() {
        return typeName;
    }

    public void setTypeName(TypeName typeName) {
        this.typeName = typeName;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof TypeName) {
            typeName = (TypeName) node;
        }
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
