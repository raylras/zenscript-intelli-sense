package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.tree.Name;
import raylras.zen.code.tree.TreeVisitor;

public class IDExpr extends Expression {

    public Name name;

    public IDExpr(Name name, Range range) {
        super(range);
        this.name = name;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChild(visitor, name);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return name.literal;
    }

}
