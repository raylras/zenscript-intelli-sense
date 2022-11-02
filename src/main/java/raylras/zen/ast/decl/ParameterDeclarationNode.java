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

    private final IdentifierNode identifier;
    private final TypeAnnotationNode typeAnnotation;
    private final ExpressionNode defaultValue;

    public ParameterDeclarationNode(IdentifierNode identifier,
                                    TypeAnnotationNode typeAnnotation,
                                    ExpressionNode defaultValue) {
        this.identifier = identifier;
        this.typeAnnotation = typeAnnotation;
        this.defaultValue = defaultValue;
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
    public String toString() {
        return identifier.getId();
    }

}
