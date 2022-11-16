package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Node;
import raylras.zen.ast.type.Statement;
import raylras.zen.ast.type.TopLevel;
import raylras.zen.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class BlockStatementNode extends ASTNode implements Statement, TopLevel {

    private List<Statement> statements;

    public BlockStatementNode() {
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Statement) {
            if (statements == null) {
                statements = new ArrayList<>();
            }
            statements.add((Statement) node);
        }
    }

    @Override
    public List<Node> getChildren() {
        return CommonUtils.toChildrenList(statements);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
