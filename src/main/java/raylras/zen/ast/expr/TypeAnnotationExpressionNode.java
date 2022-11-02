package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.TypeAnnotationNode;
import raylras.zen.ast.ASTNodeVisitor;

public class TypeAnnotationExpressionNode extends ASTNode implements ExpressionNode {

    private final ExpressionNode expr;
    private final TypeAnnotationNode typeAnnotation;

    public TypeAnnotationExpressionNode(ExpressionNode expr, TypeAnnotationNode typeAnnotation) {
        this.expr = expr;
        this.typeAnnotation = typeAnnotation;
    }

    public ExpressionNode getExpr() {
        return expr;
    }

    public TypeAnnotationNode getTypeAnnotation() {
        return typeAnnotation;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return expr + " as " + typeAnnotation;
    }

}
