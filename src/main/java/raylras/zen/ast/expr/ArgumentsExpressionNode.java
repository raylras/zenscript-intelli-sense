package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * fn(1, 2)
 * getFn()(1,2)
 */
public class ArgumentsExpressionNode extends ASTNode implements ExpressionNode {

    private ExpressionNode expr;
    private List<ExpressionNode> arguments;

    public ArgumentsExpressionNode() {
    }

    public ExpressionNode getExpr() {
        return expr;
    }

    public List<ExpressionNode> getArguments() {
        return arguments == null ? Collections.emptyList() : arguments;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof ExpressionNode) {
            if (expr == null) {
                expr = (ExpressionNode) node;
            } else {
                if (arguments == null) {
                    arguments = new ArrayList<>();
                }
                arguments.add((ExpressionNode) node);
            }
        }
    }

    @Override
    public String toString() {
        return expr + "(" + getArguments().stream().map(Object::toString).collect(Collectors.joining(", ")) + ")";
    }

}
