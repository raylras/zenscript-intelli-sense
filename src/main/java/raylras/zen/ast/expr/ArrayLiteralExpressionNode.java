package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;
import raylras.zen.ast.type.Literal;

import java.util.ArrayList;
import java.util.List;

/**
 * [1, 2, 3]
 */
public class ArrayLiteralExpressionNode extends ASTNode implements Literal, Expression {

    private List<Expression> elements;

    public ArrayLiteralExpressionNode() {
    }

    public List<Expression> getElements() {
        return elements;
    }

    public void setElements(List<Expression> elements) {
        this.elements = elements;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Expression) {
            if (elements == null) {
                elements = new ArrayList<>();
            }
            elements.add((Expression) node);
        }
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
