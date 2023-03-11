package raylras.zen.code.tree;

import raylras.zen.code.Range;
import raylras.zen.code.scope.LocalScope;
import raylras.zen.code.symbol.FunctionSymbol;
import raylras.zen.code.tree.stmt.Statement;

import java.util.List;

/**
 * Represents a constructor declaration such as "zenConstructor (params) { statement, ... }".
 * e.g. "zenConstructor() { }", "zenConstructor(i as any) { this.i = i; }".
 */
public class ConstructorDeclaration extends TreeNode implements Function, Declaration {

    public SimpleName name;
    public List<ParameterDeclaration> params;
    public List<Statement> statements;
    public FunctionSymbol symbol;
    public LocalScope localScope;

    public ConstructorDeclaration(SimpleName name, List<ParameterDeclaration> params, List<Statement> statements, Range range) {
        super(range);
        this.name = name;
        this.params = params;
        this.statements = statements;
    }

    @Override
    public SimpleName getSimpleName() {
        return name;
    }

    @Override
    public List<ParameterDeclaration> getParams() {
        return params;
    }

    @Override
    public TypeLiteral getTypeDecl() {
        return null;
    }

    @Override
    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChild(visitor, name);
            acceptChildren(visitor, params);
            acceptChildren(visitor, statements);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

}
