package raylras.zen.ast;

import org.jetbrains.annotations.Nullable;
import raylras.zen.verify.type.Type;

import java.util.List;

public class FunctionNode extends ASTNode {

    private final IdentifierNode idNode;
    private final List<ParameterNode> parameterNodeList;
    @Nullable
    private final TypeNode resultTypeNode;
    private final BlockNode blockNode;
    private Type type;

    public FunctionNode(IdentifierNode idNode, List<ParameterNode> parameterNodeList, @Nullable TypeNode resultTypeNode, BlockNode blockNode) {
        this.idNode = idNode;
        this.parameterNodeList = parameterNodeList;
        this.resultTypeNode = resultTypeNode;
        this.blockNode = blockNode;
    }

    public IdentifierNode getIdNode() {
        return idNode;
    }

    public List<ParameterNode> getParameterNodeList() {
        return parameterNodeList;
    }

    @Nullable
    public TypeNode getResultTypeNode() {
        return resultTypeNode;
    }

    public BlockNode getBlockNode() {
        return blockNode;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitFunction(this);
        idNode.accept(visitor);
        parameterNodeList.forEach(node -> node.accept(visitor));
        if (resultTypeNode != null) {
            resultTypeNode.accept(visitor);
        }
        blockNode.accept(visitor);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(idNode);
        if (type != null) {
            builder.append(" as ").append(type);
        }
        return builder.toString();
    }

}
