package raylras.zen.ast;

import raylras.zen.ast.type.Node;
import raylras.zen.ast.type.TypeName;

import java.util.Collections;
import java.util.List;

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
    public List<Node> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
