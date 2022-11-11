package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.expr.ExpressionNode;
import raylras.zen.ast.ASTNodeVisitor;

public class ExpressionStatementNode extends ASTNode implements StatementNode {

    private ExpressionNode expr;

    public ExpressionStatementNode() {
    }

    public ExpressionNode getExpr() {
        return expr;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof ExpressionNode) {
            if (expr == null) {
                expr = (ExpressionNode) node;
            }
        }
    }

    @Override
    public String toString() {
        return expr + ";";
    }

}
