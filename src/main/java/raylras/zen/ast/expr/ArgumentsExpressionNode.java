package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * fn(1, 2)
 * getFn()(1,2)
 */
public class ArgumentsExpressionNode extends ASTNode implements ExpressionNode {

    private final ExpressionNode expr;
    private final List<ExpressionNode> arguments;

    public ArgumentsExpressionNode(ExpressionNode expr, List<ExpressionNode> arguments) {
        this.expr = expr;
        this.arguments = arguments;
    }

    public ExpressionNode getExpr() {
        return expr;
    }

    public List<ExpressionNode> getArguments() {
        return arguments;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return expr + "(" + arguments.stream().map(Object::toString).collect(Collectors.joining(",")) + ")";
    }

}
