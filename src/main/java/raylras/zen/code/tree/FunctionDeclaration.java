package raylras.zen.code.tree;

import raylras.zen.code.Range;
import raylras.zen.code.scope.LocalScope;
import raylras.zen.code.symbol.FunctionSymbol;
import raylras.zen.code.tree.stmt.Statement;

import java.util.List;

/**
 * Represents a function declaration such as "function name(param, ...) as type { statement, ... }".
 * e.g. "function f() { }", "function f(a,b) as any { return a + b; }".
 *
 * @see ParameterDeclaration
 */
public class FunctionDeclaration extends TreeNode implements Function, Declaration {

    public SimpleName name;
    public List<ParameterDeclaration> params;
    public TypeLiteral returnType;
    public List<Statement> statements;
    public FunctionSymbol symbol;
    public LocalScope localScope;

    public FunctionDeclaration(SimpleName name, List<ParameterDeclaration> params, TypeLiteral returnType, List<Statement> statements, Range range) {
        super(range);
        this.name = name;
        this.params = params;
        this.returnType = returnType;
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
        return returnType;
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
            acceptChild(visitor, returnType);
            acceptChildren(visitor, statements);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

}
