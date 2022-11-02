package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

/**
 * a + b
 * a = b
 * a += b
 */
public class BinaryExpressionNode extends ASTNode implements ExpressionNode {

    private final ExpressionNode left;
    private final ExpressionNode right;
    private final Operator operator;

    public BinaryExpressionNode(ExpressionNode left,
                                ExpressionNode right,
                                Operator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public ExpressionNode getRight() {
        return right;
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return left + " " + operator + " " + right;
    }

}
