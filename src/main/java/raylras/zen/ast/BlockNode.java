package raylras.zen.ast;

import raylras.zen.ast.stmt.Statement;

import java.util.List;

public class BlockNode extends ASTNode {

    private List<Statement> statements;

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

}
