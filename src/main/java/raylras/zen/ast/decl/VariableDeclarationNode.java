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

    private final IdentifierNode identifier;
    private final TypeAnnotationNode typeAnnotation;
    private final ExpressionNode expr;

    public VariableDeclarationNode(IdentifierNode identifier,
                                   TypeAnnotationNode typeAnnotation,
                                   ExpressionNode expr) {
        this.identifier = identifier;
        this.typeAnnotation = typeAnnotation;
        this.expr = expr;
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public Optional<TypeAnnotationNode> getTypeAnnotation() {
        return Optional.ofNullable(typeAnnotation);
    }

    public Optional<ExpressionNode> getExpr() {
        return Optional.ofNullable(expr);
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
