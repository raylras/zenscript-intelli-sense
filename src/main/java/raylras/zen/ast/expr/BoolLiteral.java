package raylras.zen.ast.expr;

import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.type.Types;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.List;

public final class BoolLiteral extends BaseNode implements Expression {

    private final boolean value;

    public BoolLiteral(String value) {
        this.value = Boolean.parseBoolean(value);
        super.setType(Types.BOOL);
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return List.of();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
