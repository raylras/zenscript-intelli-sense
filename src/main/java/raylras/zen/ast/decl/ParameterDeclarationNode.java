package raylras.zen.ast.decl;

import raylras.zen.ast.*;
import raylras.zen.ast.expr.ExpressionNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.IdentifierNode;

import java.util.*;

/**
 * a as int = 1
 * full view:
 * function fn(a as int = 1, b as int = 2) { stmt; }
 */
public class ParameterDeclarationNode extends ASTNode implements DeclarationNode, VariableNode {

    private IdentifierNode identifier;
    private TypeAnnotationNode typeAnnotation;
    private ExpressionNode defaultValue;

    public ParameterDeclarationNode() {
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public Optional<TypeAnnotationNode> getTypeAnnotation() {
        return Optional.ofNullable(typeAnnotation);
    }

    public Optional<ExpressionNode> getDefaultValue() {
        return Optional.ofNullable(defaultValue);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void addChild(ASTNode node) {
        Class<? extends ASTNode> clazz = node.getClass();
        if (clazz == IdentifierNode.class) {
            if (identifier == null) {
                identifier = (IdentifierNode) node;
            }
        } else if (clazz == TypeAnnotationNode.class) {
            if (typeAnnotation == null) {
                typeAnnotation = (TypeAnnotationNode) node;
            }
        } else if (node instanceof ExpressionNode) {
            if (defaultValue == null) {
                defaultValue = (ExpressionNode) node;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(identifier);
        if (typeAnnotation != null) {
            builder.append(" as ");
            builder.append(typeAnnotation);
        }
        if (defaultValue != null) {
            builder.append(" = ");
            builder.append(defaultValue);
        }
        return builder.toString();
    }

}
