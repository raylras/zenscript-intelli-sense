package raylras.zen.ast;

import java.util.List;

public class ZenClassNode extends ClassNode {

    private String className;

    // TODO: class body structure
    private List<FieldNode> fields;
    private List<MethodNode> methods;
    private List<ConstructorNode> constructors;
    //

    public ZenClassNode(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return className;
    }

}
