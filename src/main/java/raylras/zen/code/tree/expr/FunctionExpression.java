package raylras.zen.code.tree.expr;

import raylras.zen.code.Range;
import raylras.zen.code.scope.LocalScope;
import raylras.zen.code.tree.*;
import raylras.zen.code.tree.stmt.Statement;

import java.util.List;

/**
 * Represents an expression such as "function (params) as type { statements }".
 * e.g. "function() {}", "function(i,j) as function(int,int)int { return i + j; }".
 */
public class FunctionExpression extends Expression implements Function {

    public List<ParameterDeclaration> params;
    public TypeLiteral typeDecl;
    public List<Statement> statements;
    public LocalScope localScope;

    public FunctionExpression(List<ParameterDeclaration> params, TypeLiteral typeDecl, List<Statement> statements, Range range) {
        super(range);
        this.params = params;
        this.typeDecl = typeDecl;
        this.statements = statements;
    }

    @Override
    public SimpleName getSimpleName() {
        return null;
    }

    @Override
    public List<ParameterDeclaration> getParams() {
        return params;
    }

    @Override
    public TypeLiteral getTypeDecl() {
        return typeDecl;
    }

    @Override
    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChildren(visitor, params);
            acceptChild(visitor, typeDecl);
            acceptChildren(visitor, statements);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

}
