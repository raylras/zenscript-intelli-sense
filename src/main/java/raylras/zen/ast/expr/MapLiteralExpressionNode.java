package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MapLiteralExpressionNode extends ASTNode implements LiteralExpressionNode {

    private List<MapEntryExpressionNode> entries;

    public MapLiteralExpressionNode() {
    }

    public List<MapEntryExpressionNode> getEntries() {
        return entries == null ? Collections.emptyList() : entries;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void addChild(ASTNode node) {
        if (node.getClass() == MapEntryExpressionNode.class) {
            if (entries == null) {
                entries = new ArrayList<>();
            }
            entries.add((MapEntryExpressionNode) node);
        }
    }

    @Override
    public String toString() {
        return "{" + entries.stream().map(Object::toString).collect(Collectors.joining(", ")) + "}";
    }

}
