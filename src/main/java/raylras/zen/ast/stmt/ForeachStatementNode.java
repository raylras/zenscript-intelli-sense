package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.BlockNode;
import raylras.zen.ast.TopLevelNode;
import raylras.zen.ast.decl.VariableDeclarationNode;
import raylras.zen.ast.expr.ExpressionNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * for a, b in expr { stmt; }
 */
public class ForeachStatementNode extends ASTNode implements StatementNode, TopLevelNode {

    private final List<VariableDeclarationNode> variables;
    private final ExpressionNode expr;
    private final BlockNode block;

    public ForeachStatementNode(List<VariableDeclarationNode> variables,
                                ExpressionNode expr,
                                BlockNode block) {
        this.variables = variables;
        this.expr = expr;
        this.block = block;
    }

    public List<VariableDeclarationNode> getVariables() {
        return variables;
    }

    public ExpressionNode getExpr() {
        return expr;
    }

    public BlockNode getBlock() {
        return block;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "for " + variables.stream().map(Object::toString).collect(Collectors.joining(", ")) + " in " + expr + " {...}";
    }

}
