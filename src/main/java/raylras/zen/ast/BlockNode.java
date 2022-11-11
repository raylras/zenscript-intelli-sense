package raylras.zen.ast;

import raylras.zen.ast.stmt.StatementNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BlockNode extends ASTNode implements StatementNode {

    private List<StatementNode> statements;

    public BlockNode() {
    }

    public List<StatementNode> getStatements() {
        return statements;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof StatementNode) {
            if (statements == null) {
                statements = new ArrayList<>();
            }
            statements.add((StatementNode) node);
        }
    }

    @Override
    public String toString() {
        return "{" + "\n" + statements.stream().map(Objects::toString).collect(Collectors.joining("\n")) + "\n" + "}";
    }

}
