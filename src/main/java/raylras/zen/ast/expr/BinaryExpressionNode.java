package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

/**
 * a + b
 * a = b
 * a += b
 */
public class BinaryExpressionNode extends ASTNode implements ExpressionNode {

    private ExpressionNode left;
    private ExpressionNode right;
    private Operator operator;

    public BinaryExpressionNode(Operator operator) {
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
    public void addChild(ASTNode node) {
        if (node instanceof ExpressionNode) {
            if (left == null) {
                left = (ExpressionNode) node;
            } else if (right == null) {
                right = (ExpressionNode) node;
            }
        }
    }

    @Override
    public String toString() {
        return left + " " + operator + " " + right;
    }

}
