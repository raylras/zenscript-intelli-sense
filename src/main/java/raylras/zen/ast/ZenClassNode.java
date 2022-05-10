package raylras.zen.ast;

import java.util.List;

public class ZenClassNode extends ASTNode {

    private final IdentifierNode idNode;
    private final List<FieldNode> fieldNodeList;
    private final List<ConstructorNode> constructorNodeList;

    private final List<FunctionNode> functionNodeList;

    public ZenClassNode(IdentifierNode idNode, List<FieldNode> fieldNodeList, List<ConstructorNode> constructorNodeList, List<FunctionNode> functionNodeList) {
        this.idNode = idNode;
        this.fieldNodeList = fieldNodeList;
        this.constructorNodeList = constructorNodeList;
        this.functionNodeList = functionNodeList;
    }

    public IdentifierNode getIdNode() {
        return idNode;
    }

    public List<FieldNode> getFieldNodeList() {
        return fieldNodeList;
    }

    public List<ConstructorNode> getConstructorNodeList() {
        return constructorNodeList;
    }

    public List<FunctionNode> getFunctionNodeList() {
        return functionNodeList;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitZenClass(this);
        idNode.accept(visitor);
        fieldNodeList.forEach(node -> node.accept(visitor));
        constructorNodeList.forEach(node -> node.accept(visitor));
        functionNodeList.forEach(node -> node.accept(visitor));
    }

    @Override
    public String toString() {
        return idNode.getName();
    }

}
