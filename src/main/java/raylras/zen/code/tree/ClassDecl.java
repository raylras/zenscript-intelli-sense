package raylras.zen.code.tree;

import raylras.zen.code.Range;
import raylras.zen.code.scope.LocalScope;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.tree.stmt.VariableDecl;

import java.util.List;

/**
 * Represents a class declaration such as "zenClass name { }".
 * e.g. "zenClass Foo { var foo = null; function bar() { } }".
 */
public class ClassDecl extends TreeNode {

    public Name name;
    public List<VariableDecl> fields;
    public List<ConstructorDecl> constructors;
    public List<FunctionDecl> methods;
    public ClassSymbol symbol;
    public LocalScope localScope;

    public ClassDecl(Name name, List<VariableDecl> fields, List<ConstructorDecl> constructors, List<FunctionDecl> methods, Range range) {
        super(range);
        this.name = name;
        this.constructors = constructors;
        this.fields = fields;
        this.methods = methods;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChild(visitor, name);
            acceptChildren(visitor, fields);
            acceptChildren(visitor, constructors);
            acceptChildren(visitor, methods);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

}
