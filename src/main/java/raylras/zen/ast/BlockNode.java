package raylras.zen.ast;

import raylras.zen.ast.stmt.Statement;

import java.util.List;

public class BlockNode extends ASTNode {

    private final List<Statement> statements;

    public BlockNode(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitBlock(this);
        statements.forEach(node -> node.accept(visitor));
    }

}
