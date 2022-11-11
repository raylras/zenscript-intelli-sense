package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.BlockNode;
import raylras.zen.ast.expr.ExpressionNode;
import raylras.zen.ast.ASTNodeVisitor;

/**
 * while (expr) { stmt; }
 */
public class WhileStatementNode extends ASTNode implements StatementNode {

    private ExpressionNode expr;
    private BlockNode block;

    public WhileStatementNode() {
    }

    public ExpressionNode getExpr() {
        return expr;
    }

    public BlockNode getBlock() {
        return block;
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
        } else if (node.getClass() == BlockNode.class) {
            if (block == null) {
                block = (BlockNode) node;
            }
        }
    }

    @Override
    public String toString() {
        return "while " + expr + " " + block;
    }

}
