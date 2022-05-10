package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTVisitor;
import raylras.zen.ast.BlockNode;
import raylras.zen.ast.VariableNode;
import raylras.zen.ast.expr.Expression;

import java.util.List;

public class ForeachStatement extends Statement {

    private final List<VariableNode> variableNodeList;
    private final Expression expr;

    private final BlockNode blockNode;

    public ForeachStatement(List<VariableNode> variableNodeList, Expression expr, BlockNode blockNode) {
        this.variableNodeList = variableNodeList;
        this.expr = expr;
        this.blockNode = blockNode;
    }

    public List<VariableNode> getVariableNodeList() {
        return variableNodeList;
    }

    public Expression getExpr() {
        return expr;
    }

    public BlockNode getBlockNode() {
        return blockNode;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitForeachStatement(this);
        variableNodeList.forEach(node -> node.accept(visitor));
        expr.accept(visitor);
        blockNode.accept(visitor);
    }

}
