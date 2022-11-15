package raylras.zen.ast;

import raylras.zen.ast.type.TypeName;

public class TypeNameNode extends ASTNode implements TypeName {

    private String value;

    public TypeNameNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void addChild(ASTNode node) {
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
