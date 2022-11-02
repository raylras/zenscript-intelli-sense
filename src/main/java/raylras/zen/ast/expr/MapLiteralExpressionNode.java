package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.List;
import java.util.stream.Collectors;

public class MapLiteralExpressionNode extends ASTNode implements LiteralExpressionNode {

    private final List<MapEntryExpressionNode> entries;

    public MapLiteralExpressionNode(List<MapEntryExpressionNode> entries) {
        this.entries = entries;
    }

    public List<MapEntryExpressionNode> getEntries() {
        return entries;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "{" + entries.stream().map(Object::toString).collect(Collectors.joining(", ")) + "}";
    }

}
