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
 * @see ParameterDecl
 */
public class FunctionDecl extends TreeNode implements Function, Declaration {

    public Name name;
    public List<ParameterDecl> params;
    public TypeLiteral returnType;
    public List<Statement> statements;
    public FunctionSymbol symbol;
    public LocalScope localScope;

    public FunctionDecl(Name name, List<ParameterDecl> params, TypeLiteral returnType, List<Statement> statements, Range range) {
        super(range);
        this.name = name;
        this.params = params;
        this.returnType = returnType;
        this.statements = statements;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public List<ParameterDecl> getParams() {
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
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitFunctionDecl(this);
    }

    @Override
    public String toString() {
        return new Pretty().visitFunctionDecl(this);
    }

}
