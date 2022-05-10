package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

import java.util.List;

public class ArrayLiteralExpression extends Expression {

    private final List<Expression> elements;

    public ArrayLiteralExpression(List<Expression> elements) {
        this.elements = elements;
    }

    public List<Expression> getElements() {
        return elements;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitArrayLiteralExpression(this);
        elements.forEach(node -> node.accept(visitor));
    }

}
