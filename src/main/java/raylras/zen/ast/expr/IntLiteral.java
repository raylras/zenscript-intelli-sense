package raylras.zen.ast.expr;

import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.type.Types;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collections;
import java.util.List;

public final class IntLiteral extends BaseNode implements Expression{

    private final long value;

    public IntLiteral(String value) {
        this.value = Long.decode(value);
        super.setType(Types.INT);
    }

    public long getValue() {
        return value;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return null;
    }

    @Override
    public List<? extends Node> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
