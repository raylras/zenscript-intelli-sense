package raylras.zen.code.tree;

import raylras.zen.code.Range;

/**
 * Represents an import declaration such as "import name.name. ... as name".
 * e.g. "import foo.bar as b;".
 */
public class ImportDeclaration extends TreeNode implements Declaration {

    public Name fullName;
    public SimpleName alias;

    public ImportDeclaration(Name fullName, SimpleName alias, Range range) {
        super(range);
        this.fullName = fullName;
        this.alias = alias;
    }

    @Override
    public SimpleName getSimpleName() {
        return (alias != null) ? alias : fullName.getSimpleName();
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChild(visitor, fullName);
            acceptChild(visitor, alias);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

}
