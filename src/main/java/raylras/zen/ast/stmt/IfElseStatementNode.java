package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.expr.ExpressionNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.*;

/**
 * if (expr) { thenStmt; } else { elseStmt; }
 */
public class IfElseStatementNode extends ASTNode implements StatementNode {

    private ExpressionNode expr;
    private StatementNode thenStmt;
    private StatementNode elseStmt;

    public IfElseStatementNode() {
    }

    public ExpressionNode getExpr() {
        return expr;
    }

    public StatementNode getThenStmt() {
        return thenStmt;
    }

    public Optional<StatementNode> getElseStmt() {
        return Optional.ofNullable(elseStmt);
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
        } else if (node instanceof StatementNode) {
            if (thenStmt == null) {
                thenStmt = (StatementNode) node;
            } else if (elseStmt == null) {
                elseStmt = (StatementNode) node;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("if");
        builder.append(" ");
        builder.append(expr);
        builder.append(" ");
        builder.append(thenStmt);
        if (elseStmt != null) {
            builder.append(" else ");
            builder.append(elseStmt);
        }
        return builder.toString();
    }

}
