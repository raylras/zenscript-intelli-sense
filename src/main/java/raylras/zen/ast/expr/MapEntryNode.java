package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;
import raylras.zen.ast.type.MapEntry;
import raylras.zen.ast.type.Node;
import raylras.zen.util.CommonUtils;

import java.util.List;

public class MapEntryNode extends ASTNode implements MapEntry {

    private Expression key;
    private Expression value;

    public MapEntryNode() {
    }

    public Expression getKey() {
        return key;
    }

    public void setKey(Expression key) {
        this.key = key;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Expression) {
            if (key == null) {
                key = (Expression) node;
            } else if (value == null) {
                value = (Expression) node;
            }
        }
    }

    @Override
    public List<Node> getChildren() {
        return CommonUtils.toChildrenList(key, value);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
