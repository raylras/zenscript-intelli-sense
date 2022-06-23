package raylras.zen.ast.stmt;

import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collections;
import java.util.List;

public final class BreakStatement extends BaseNode implements Statement {

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "break;";
    }

}
