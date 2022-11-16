package raylras.zen.ast;

import raylras.zen.ast.type.Expression;
import raylras.zen.ast.type.Identifier;
import raylras.zen.ast.type.Node;

import java.util.Collections;
import java.util.List;

public class IdentifierNode extends ASTNode implements Identifier, Expression {

    private String value;

    public IdentifierNode(String value) {
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
