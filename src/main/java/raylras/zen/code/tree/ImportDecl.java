package raylras.zen.code.tree;

import raylras.zen.code.Range;

import java.util.List;

/**
 * Represents an import declaration such as "import name.name. ... as name".
 * e.g. "import foo.bar as b;".
 */
public class ImportDecl extends TreeNode implements Declaration {

    public List<Name> fullName;
    public Name alias;

    public ImportDecl(List<Name> fullName, Name alias, Range range) {
        super(range);
        this.fullName = fullName;
        this.alias = alias;
    }

    public Name getSimpleName() {
        return (alias != null) ? alias : fullName.get(fullName.size() - 1);
    }

    @Override
    public Name getName() {
        return getSimpleName();
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitImportDecl(this);
    }

}
