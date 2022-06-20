package raylras.zen.ast.expr;

import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.type.Types;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collections;
import java.util.List;

public final class FloatLiteral extends BaseNode implements Expression {

    private final double value;

    public FloatLiteral(String value) {
        this.value = Double.parseDouble(value);
        super.setType(Types.FLOAT);
    }

    public double getValue() {
        return value;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return null;
    }

    @Override
    public List<Node> getChildren() {
        return Collections.emptyList();
    }

}
