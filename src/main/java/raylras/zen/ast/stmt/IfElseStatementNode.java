package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.expr.ExpressionNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.*;

/**
 * if (expr) { thenStmt; } else { elseStmt; }
 */
public class IfElseStatementNode extends ASTNode implements StatementNode {

    private final ExpressionNode expr;
    private final StatementNode thenStmt;
    private final StatementNode elseStmt;

    public IfElseStatementNode(ExpressionNode expr,
                               StatementNode thenStmt,
                               StatementNode elseStmt) {
        this.expr = expr;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
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
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("if ").append(expr).append(" {...}");
        if (elseStmt != null) {
            builder.append(" {...}");
        }
        return builder.toString();
    }

}
