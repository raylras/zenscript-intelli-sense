package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTVisitor;
import raylras.zen.ast.BlockNode;

public class BlockStatement extends Statement {

    private final BlockNode blockNode;

    public BlockStatement(BlockNode blockNode) {
        this.blockNode = blockNode;
    }

    public BlockNode getBlockNode() {
        return blockNode;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitStatement(this);
        blockNode.accept(visitor);
    }

}
