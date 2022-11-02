package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

public class UnaryExpressionNode extends ASTNode implements ExpressionNode {

    private final ExpressionNode expr;
    private final Operator operator;

    public UnaryExpressionNode(ExpressionNode expr,Operator operator) {
        this.expr = expr;
        this.operator = operator;
    }

    public ExpressionNode getExpr() {
        return expr;
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
        return operator.toString() + expr;
    }

}
