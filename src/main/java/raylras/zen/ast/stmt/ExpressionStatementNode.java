package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.expr.ExpressionNode;
import raylras.zen.ast.ASTNodeVisitor;

public class ExpressionStatementNode extends ASTNode implements StatementNode {

    private final ExpressionNode expr;

    public ExpressionStatementNode(ExpressionNode expr) {
        this.expr = expr;
    }

    public ExpressionNode getExpr() {
        return expr;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return expr + ";";
    }

}
