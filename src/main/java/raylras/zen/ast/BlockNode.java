package raylras.zen.ast;

import raylras.zen.ast.stmt.StatementNode;

import java.util.List;

public class BlockNode extends ASTNode implements StatementNode {

    private final List<StatementNode> statements;

    public BlockNode(List<StatementNode> statements) {
        this.statements = statements;
    }

    public List<StatementNode> getStatements() {
        return statements;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "{...}";
    }

}
