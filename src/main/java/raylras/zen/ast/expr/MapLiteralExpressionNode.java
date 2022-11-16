package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;
import raylras.zen.ast.type.Literal;
import raylras.zen.ast.type.MapEntry;
import raylras.zen.ast.type.Node;
import raylras.zen.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class MapLiteralExpressionNode extends ASTNode implements Literal, Expression {

    private List<MapEntry> entries;

    public MapLiteralExpressionNode() {
    }

    public List<MapEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<MapEntry> entries) {
        this.entries = entries;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof MapEntry) {
            if (entries == null) {
                entries = new ArrayList<>();
            }
            entries.add((MapEntry) node);
        }
    }

    @Override
    public List<Node> getChildren() {
        return CommonUtils.toChildrenList(entries);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
