package raylras.zen.ast;

import java.util.List;

public class ZenClassNode extends ClassNode {

    private IDNode nameNode;
    private List<FieldNode> fieldNodes;
    private List<MethodNode> methodNodes;
    private List<ConstructorNode> constructorNodes;

    public IDNode getNameNode() {
        return nameNode;
    }

    public void setNameNode(IDNode nameNode) {
        this.nameNode = nameNode;
    }

    public List<FieldNode> getFieldNodes() {
        return fieldNodes;
    }

    public void setFieldNodes(List<FieldNode> fieldNodes) {
        this.fieldNodes = fieldNodes;
    }

    public List<MethodNode> getMethodNodes() {
        return methodNodes;
    }

    public void setMethodNodes(List<MethodNode> methodNodes) {
        this.methodNodes = methodNodes;
    }

    public List<ConstructorNode> getConstructorNodes() {
        return constructorNodes;
    }

    public void setConstructorNodes(List<ConstructorNode> constructorNodes) {
        this.constructorNodes = constructorNodes;
    }

    @Override
    public String getClassName() {
        return nameNode.getName();
    }

    @Override
    public String toString() {
        return nameNode.getName();
    }

}
