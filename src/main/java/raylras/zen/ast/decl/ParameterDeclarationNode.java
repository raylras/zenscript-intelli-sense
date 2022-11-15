package raylras.zen.ast.decl;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.*;

/**
 * a as int = 1
 * full view:
 * function fn(a as int = 1, b as int = 2) { stmt; }
 */
public class ParameterDeclarationNode extends ASTNode implements Parameter, Variable, Declaration {

    private Identifier identifier;
    private TypeAnnotation typeAnnotation;
    private Expression initializer;

    public ParameterDeclarationNode() {
    }

    public Declarator getDeclarator() {
        return Declarator.NONE;
    }

    public void setDeclarator(Declarator declarator) {
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public TypeAnnotation getTypeAnnotation() {
        return typeAnnotation;
    }

    public void setTypeAnnotation(TypeAnnotation typeAnnotation) {
        this.typeAnnotation = typeAnnotation;
    }

    public Expression getInitializer() {
        return initializer;
    }

    public void setInitializer(Expression initializer) {
        this.initializer = initializer;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Identifier) {
            identifier = (Identifier) node;
        } else if (node instanceof TypeAnnotation) {
            typeAnnotation = (TypeAnnotation) node;
        } else if (node instanceof Expression) {
            initializer = (Expression) node;
        }
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
