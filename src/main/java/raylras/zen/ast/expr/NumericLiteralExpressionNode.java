package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;
import raylras.zen.ast.type.Literal;
import raylras.zen.ast.type.Node;

import java.util.Collections;
import java.util.List;

/**
 * 100
 * 1.0f
 * 0xABCD
 */
public class NumericLiteralExpressionNode extends ASTNode implements Literal, Expression {

    private String literal;
    private NumericKind numericKind;

    public NumericLiteralExpressionNode(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    public NumericKind getNumericKind() {
        return numericKind;
    }

    public void setNumericKind(NumericKind numericKind) {
        this.numericKind = numericKind;
    }

    @Override
    public void addChild(ASTNode node) {
    }

    @Override
    public List<Node> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
