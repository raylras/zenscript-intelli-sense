package raylras.zen.ast.expr;

import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.List;

public final class IntLiteral extends BaseNode implements Expression{

    private final String value;

    public IntLiteral(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return null;
    }

    @Override
    public List<? extends Node> getChildren() {
        return List.of();
    }

    @Override
    public String toString() {
        return value;
    }

}
