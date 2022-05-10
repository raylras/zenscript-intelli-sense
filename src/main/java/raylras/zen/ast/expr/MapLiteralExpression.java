package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

import java.util.List;

public class MapLiteralExpression extends Expression {

    private final List<MapEntryExpression> entryList;

    public MapLiteralExpression(List<MapEntryExpression> entryList) {
        this.entryList = entryList;
    }

    public List<MapEntryExpression> getEntryList() {
        return entryList;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitMapLiteralExpression(this);
        entryList.forEach(node -> node.accept(visitor));
    }

}
