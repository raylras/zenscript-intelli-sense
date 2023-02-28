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
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitIDExpr(this);
    }

}
