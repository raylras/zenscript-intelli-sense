package raylras.zen.code.tree.stmt;

import raylras.zen.code.Range;
import raylras.zen.code.symbol.VariableSymbol;
import raylras.zen.code.tree.*;
import raylras.zen.code.tree.expr.Expression;

/**
 * Represents a statement such as "var name as type = expr".
 * e.g. "var a;", "val b as any = 1;".
 */
public class VariableDecl extends Statement implements Variable, Declaration {

    public Declarator declarator;
    public Name name;
    public TypeLiteral typeDecl;
    public Expression init;
    public VariableSymbol symbol;

    public VariableDecl(Declarator declarator, Name name, TypeLiteral typeDecl, Expression init, Range range) {
        super(range);
        this.declarator = declarator;
        this.name = name;
        this.typeDecl = typeDecl;
        this.init = init;
    }

    @Override
    public Declarator getDeclarator() {
        return declarator;
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
        return init;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChild(visitor, name);
            acceptChild(visitor, typeDecl);
            acceptChild(visitor, init);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

}
