package raylras.zen.ast;

import java.util.List;

public class ConstructorNode extends ASTNode {

    private List<ParameterNode> parameterNodes;
    private BlockNode blockNode;

    public List<ParameterNode> getParameterNodes() {
        return parameterNodes;
    }

    public void setParameterNodes(List<ParameterNode> parameterNodes) {
        this.parameterNodes = parameterNodes;
    }

    public BlockNode getBlockNode() {
        return blockNode;
    }

    public void setBlockNode(BlockNode blockNode) {
        this.blockNode = blockNode;
    }

}
