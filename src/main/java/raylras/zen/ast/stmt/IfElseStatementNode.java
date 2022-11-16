package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;
import raylras.zen.ast.type.Node;
import raylras.zen.ast.type.Statement;
import raylras.zen.ast.type.TopLevel;
import raylras.zen.util.CommonUtils;

import java.util.List;

/**
 * if (expr) { thenStmt; } else { elseStmt; }
 */
public class IfElseStatementNode extends ASTNode implements Statement, TopLevel {

    private Expression expr;
    private Statement thenStmt;
    private Statement elseStmt;

    public IfElseStatementNode() {
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    public Statement getThenStmt() {
        return thenStmt;
    }

    public void setThenStmt(Statement thenStmt) {
        this.thenStmt = thenStmt;
    }

    public Statement getElseStmt() {
        return elseStmt;
    }

    public void setElseStmt(Statement elseStmt) {
        this.elseStmt = elseStmt;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Expression) {
            expr = (Expression) node;
        } else if (node instanceof Statement) {
            if (thenStmt == null) {
                thenStmt = (Statement) node;
            } else if (elseStmt == null) {
                elseStmt = (Statement) node;
            }
        }
    }

    @Override
    public List<Node> getChildren() {
        return CommonUtils.toChildrenList(expr, thenStmt, elseStmt);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
