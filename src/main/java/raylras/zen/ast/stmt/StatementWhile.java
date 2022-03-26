package raylras.zen.ast.stmt;

import raylras.zen.ast.BlockNode;
import raylras.zen.ast.expr.Expression;

public class StatementWhile extends Statement {

    private Expression expr;
    private BlockNode blockNode;

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    public BlockNode getBlockNode() {
        return blockNode;
    }

    public void setBlockNode(BlockNode blockNode) {
        this.blockNode = blockNode;
    }

}
