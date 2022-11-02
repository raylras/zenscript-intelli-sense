package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * [1, 2, 3]
 */
public class ArrayLiteralExpressionNode extends ASTNode implements LiteralExpressionNode {

    private final List<ExpressionNode> elements;

    public ArrayLiteralExpressionNode(List<ExpressionNode> elements) {
        this.elements = elements;
    }

    public List<ExpressionNode> getElements() {
        return elements;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "[" + elements.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]";
    }

}
