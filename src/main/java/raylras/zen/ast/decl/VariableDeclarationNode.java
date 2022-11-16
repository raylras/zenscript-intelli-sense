package raylras.zen.ast.decl;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.*;
import raylras.zen.util.CommonUtils;

import java.util.List;

/**
 * var a = 1;
 * for b in expr { stmt; }
 */
public class VariableDeclarationNode extends ASTNode implements Variable, Declaration, Statement, TopLevel {

    private Declarator declarator;
    private Identifier identifier;
    private TypeAnnotation typeAnnotation;
    private Expression initializer;

    public VariableDeclarationNode() {
    }

    public Declarator getDeclarator() {
        return declarator;
    }

    public void setDeclarator(Declarator declarator) {
        this.declarator = declarator;
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
    public List<Node> getChildren() {
        return CommonUtils.toChildrenList(identifier, typeAnnotation, initializer);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
