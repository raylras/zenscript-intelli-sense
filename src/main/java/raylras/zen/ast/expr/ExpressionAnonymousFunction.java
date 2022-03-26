package raylras.zen.ast.expr;

import raylras.zen.ast.BlockNode;
import raylras.zen.ast.ParameterNode;
import raylras.zen.ast.TypeNode;

import java.util.List;

public class ExpressionAnonymousFunction extends Expression {

    private List<ParameterNode> parameterNodes;
    private TypeNode returnTypeNode;
    private BlockNode blockNode;

    public List<ParameterNode> getParameterNodes() {
        return parameterNodes;
    }

    public void setParameterNodes(List<ParameterNode> parameterNodes) {
        this.parameterNodes = parameterNodes;
    }

    public TypeNode getReturnTypeNode() {
        return returnTypeNode;
    }

    public void setReturnTypeNode(TypeNode returnTypeNode) {
        this.returnTypeNode = returnTypeNode;
    }

    public BlockNode getBlockNode() {
        return blockNode;
    }

    public void setBlockNode(BlockNode blockNode) {
        this.blockNode = blockNode;
    }

}
