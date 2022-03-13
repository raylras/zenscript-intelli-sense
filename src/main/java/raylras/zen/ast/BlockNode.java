package raylras.zen.ast;

import raylras.zen.ast.scope.Scope;
import raylras.zen.ast.scope.VariableScope;
import raylras.zen.ast.stmt.Statement;

import java.util.ArrayList;
import java.util.List;

public class BlockNode extends ASTNode implements Scope, IErrorNode {

    private Scope parent;
    private List<Statement> statements;
    private VariableScope variables;

    public BlockNode() {
        this.statements = new ArrayList<>();
        this.variables = new VariableScope();
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public Scope getParent() {
        return parent;
    }

    @Override
    public void setParent(Scope parent) {
        this.parent = parent;
    }

    @Override
    public ASTNode resolve(String name) {
        return variables.resolve(name);
    }

    @Override
    public void define(String name, ASTNode node) {
        variables.define(name, node);
    }

    @Override
    public void addStatement(Statement statement) {
        statements.add(statement);
    }

    @Override
    public List<ErrorNode> getErrors() {
        return variables.getErrors();
    }

}
