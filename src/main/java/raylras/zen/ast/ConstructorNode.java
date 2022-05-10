package raylras.zen.ast;

import java.util.List;

public class ConstructorNode extends ASTNode {

    private final List<ParameterNode> parameterNodeList;
    private final BlockNode blockNode;

    public ConstructorNode(List<ParameterNode> parameterNodeList, BlockNode blockNode) {
        this.parameterNodeList = parameterNodeList;
        this.blockNode = blockNode;
    }

    public List<ParameterNode> getParameterNodeList() {
        return parameterNodeList;
    }

    public BlockNode getBlockNode() {
        return blockNode;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitConstructor(this);
        parameterNodeList.forEach(node -> node.accept(visitor));
        blockNode.accept(visitor);
    }

    @Override
    public String toString() {
        return "zenConstructor";
    }

}
