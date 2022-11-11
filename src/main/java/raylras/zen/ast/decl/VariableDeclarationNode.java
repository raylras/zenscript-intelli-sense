package raylras.zen.ast.decl;

import raylras.zen.ast.*;
import raylras.zen.ast.expr.ExpressionNode;
import raylras.zen.ast.IdentifierNode;
import raylras.zen.ast.stmt.StatementNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.*;

/**
 * var a = 1;
 * for b in expr { stmt; }
 */
public class VariableDeclarationNode extends ASTNode implements DeclarationNode, StatementNode, TopLevelNode {

    private IdentifierNode identifier;
    private TypeAnnotationNode typeAnnotation;
    private ExpressionNode initializer;

    public VariableDeclarationNode() {
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public Optional<TypeAnnotationNode> getTypeAnnotation() {
        return Optional.ofNullable(typeAnnotation);
    }

    public Optional<ExpressionNode> getInitializer() {
        return Optional.ofNullable(initializer);
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
            if (initializer == null) {
                initializer = (ExpressionNode) node;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("var");
        builder.append(" ");
        builder.append(identifier);
        if (typeAnnotation != null) {
            builder.append(" as ");
            builder.append(typeAnnotation);
        }
        if (initializer != null) {
            builder.append(" = ");
            builder.append(initializer);
        }
        builder.append(";");
        return builder.toString();
    }

}
