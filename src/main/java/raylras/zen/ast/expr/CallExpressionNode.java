package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * fn(1, 2)
 * getFn()(1,2)
 */
public class CallExpressionNode extends ASTNode implements Expression {

    private Expression left;
    private List<Expression> arguments;

    public CallExpressionNode() {
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public void setArguments(List<Expression> arguments) {
        this.arguments = arguments;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Expression) {
            if (left == null) {
                left = (Expression) node;
            } else {
                if (arguments == null) {
                    arguments = new ArrayList<>();
                }
                arguments.add((Expression) node);
            }
        }
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
