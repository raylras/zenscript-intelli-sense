package raylras.zen.ast;

import raylras.zen.ast.type.Type;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionNode extends ASTNode {

    private IDNode nameNode;
    private List<ParameterNode> parameterNodes;
    private TypeNode returnTypeNode;
    private BlockNode blockNode;

    private Type returnType;

    public IDNode getNameNode() {
        return nameNode;
    }

    public void setNameNode(IDNode nameNode) {
        this.nameNode = nameNode;
    }

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(nameNode.getName());
        builder.append('(');
        builder.append(parameterNodes.stream().map(ParameterNode::toString).collect(Collectors.joining(",")));
        builder.append(')');
        if (returnType != null) {
            builder.append(" as ").append(returnType.getTypeName());
        }
        if (returnTypeNode != null) {
            builder.append(" as ").append(returnTypeNode.getTypeName());
        }
        return builder.toString();
    }

}
