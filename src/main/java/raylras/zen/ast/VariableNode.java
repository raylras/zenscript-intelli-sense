package raylras.zen.ast;

import raylras.zen.ast.type.Type;

public class VariableNode extends ASTNode {

    private String name;
    private Type type;
    private boolean isFinal;

    public VariableNode(String name) {
        this.name = name;
    }

    public VariableNode(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public String getNameAndType() {
        return name + " as " + type;
    }

    @Override
    public String toString() {
        return name;
    }

}
