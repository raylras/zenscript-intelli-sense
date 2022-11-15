package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;
import raylras.zen.ast.type.MapEntry;

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
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
