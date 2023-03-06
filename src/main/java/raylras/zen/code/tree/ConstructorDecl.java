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
public class ConstructorDecl extends TreeNode implements Function, Declaration {

    public Name name;
    public List<ParameterDecl> params;
    public List<Statement> statements;
    public FunctionSymbol symbol;
    public LocalScope localScope;

    public ConstructorDecl(Name name, List<ParameterDecl> params, List<Statement> statements, Range range) {
        super(range);
        this.name = name;
        this.params = params;
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
        return null;
    }

    @Override
    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitConstructorDecl(this);
    }

    @Override
    public String toString() {
        return new Pretty().visitConstructorDecl(this);
    }

}
