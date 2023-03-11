package raylras.zen.code.tree.stmt;

import raylras.zen.code.Range;
import raylras.zen.code.scope.LocalScope;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;
import raylras.zen.code.tree.expr.Expression;

import java.util.List;

/**
 * Represents a statement such as "for name, ... in expr { statement, ... }".
 * e.g. "for i in arr { print(i); }", "for key, value in map { }".
 */
public class Foreach extends Statement {

    public List<VariableDeclaration> variables;
    public Expression expression;
    public List<Statement> statements;
    public LocalScope localScope;

    public Foreach(List<VariableDeclaration> variables, Expression expr, List<Statement> statements, Range range) {
        super(range);
        this.variables = variables;
        this.expression = expr;
        this.statements = statements;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        boolean visitChildren = visitor.visit(this);
        if (visitChildren) {
            acceptChildren(visitor, variables);
            acceptChild(visitor, expression);
            acceptChildren(visitor, statements);
        }
        visitor.afterVisit(this);
    }

    @Override
    public String toString() {
        return new Pretty(this).toString();
    }

}
