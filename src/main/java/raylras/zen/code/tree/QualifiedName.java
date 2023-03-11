package raylras.zen.code.tree;

import raylras.zen.code.Range;

public class QualifiedName extends Name {

    public Name qualifier;
    public SimpleName name;

    public QualifiedName(Name qualifier, SimpleName name, Range range) {
        super(range);
        this.qualifier = qualifier;
        this.name = name;
    }

    @Override
    public SimpleName getSimpleName() {
        return name;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChild(visitor, qualifier);
            acceptChild(visitor, name);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }
}
