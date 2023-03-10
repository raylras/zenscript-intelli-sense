package raylras.zen.code.tree;

import raylras.zen.code.Range;
import raylras.zen.code.symbol.VariableSymbol;
import raylras.zen.code.tree.expr.Expression;

/**
 * Represents a parameter such as "name as type = expr".
 * e.g. "i", "i as int = 2".
 *
 * @see FunctionDecl
 */
public class ParameterDecl extends TreeNode implements Variable, Declaration {

    public Name name;
    public TypeLiteral typeDecl;
    public Expression defaultValue;
    public VariableSymbol symbol;

    public ParameterDecl(Name name, TypeLiteral typeDecl, Expression defaultValue, Range range) {
        super(range);
        this.name = name;
        this.typeDecl = typeDecl;
        this.defaultValue = defaultValue;
    }

    @Override
    public Declarator getDeclarator() {
        return Declarator.NONE;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public TypeLiteral getTypeDecl() {
        return typeDecl;
    }

    @Override
    public Expression getInit() {
        return defaultValue;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChild(visitor, name);
            acceptChild(visitor, typeDecl);
            acceptChild(visitor, defaultValue);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

}
