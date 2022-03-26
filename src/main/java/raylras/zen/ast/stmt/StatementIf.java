package raylras.zen.ast.stmt;

import raylras.zen.ast.BlockNode;
import raylras.zen.ast.expr.Expression;

public class StatementIf extends Statement {

    private Expression expr;
    private Statement ifStmt;
    private BlockNode ifBlock;
    private Statement elseStmt;
    private BlockNode elseBlock;

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    public Statement getIfStmt() {
        return ifStmt;
    }

    public void setIfStmt(Statement ifStmt) {
        this.ifStmt = ifStmt;
    }

    public BlockNode getIfBlock() {
        return ifBlock;
    }

    public void setIfBlock(BlockNode ifBlock) {
        this.ifBlock = ifBlock;
    }

    public Statement getElseStmt() {
        return elseStmt;
    }

    public void setElseStmt(Statement elseStmt) {
        this.elseStmt = elseStmt;
    }

    public BlockNode getElseBlock() {
        return elseBlock;
    }

    public void setElseBlock(BlockNode elseBlock) {
        this.elseBlock = elseBlock;
    }

}
