package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.BlockNode;
import raylras.zen.ast.TopLevelNode;
import raylras.zen.ast.decl.VariableDeclarationNode;
import raylras.zen.ast.expr.ExpressionNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * for a, b in expr { stmt; }
 */
public class ForeachStatementNode extends ASTNode implements StatementNode, TopLevelNode {

    private List<VariableDeclarationNode> variables;
    private ExpressionNode expr;
    private BlockNode block;

    public ForeachStatementNode() {
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
    public void addChild(ASTNode node) {
        Class<? extends ASTNode> clazz = node.getClass();
        if (clazz == VariableDeclarationNode.class) {
            if (variables == null) {
                variables = new ArrayList<>();
            }
            variables.add((VariableDeclarationNode) node);
        } else if (node instanceof ExpressionNode) {
            if (expr == null) {
                expr = (ExpressionNode) node;
            }
        } else if (clazz == BlockNode.class) {
            if (block == null) {
                block = (BlockNode) node;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("for");
        builder.append(" ");
        builder.append(variables.stream().map(Object::toString).collect(Collectors.joining(", ")));
        builder.append(" in ");
        builder.append(expr);
        builder.append(" ");
        builder.append(block);
        return builder.toString();
    }

}
