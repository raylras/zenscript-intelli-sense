package raylras.zen.ast.expr;

import java.util.List;

public class ExpressionArrayInit extends Expression {

    private List<Expression> elements;

    public List<Expression> getElements() {
        return elements;
    }

    public void setElements(List<Expression> elements) {
        this.elements = elements;
    }

}
