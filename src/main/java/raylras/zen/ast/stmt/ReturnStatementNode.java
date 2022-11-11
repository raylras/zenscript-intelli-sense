package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.expr.ExpressionNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.Optional;

public class ReturnStatementNode extends ASTNode implements StatementNode {

    private ExpressionNode expr;

    public ReturnStatementNode() {
    }

    public Optional<ExpressionNode> getExpr() {
        return Optional.ofNullable(expr);
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
        StringBuilder builder = new StringBuilder();
        builder.append("return");
        if (expr != null) {
            builder.append(" ");
            builder.append(expr);
        }
        builder.append(";");
        return builder.toString();
    }

}
