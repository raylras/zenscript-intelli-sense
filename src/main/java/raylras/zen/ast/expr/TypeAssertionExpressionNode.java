package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;
import raylras.zen.ast.type.TypeName;

public class TypeAssertionExpressionNode extends ASTNode implements Expression {

    private Expression expr;
    private TypeName typeName;

    public TypeAssertionExpressionNode() {
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    public TypeName getTypeName() {
        return typeName;
    }

    public void setTypeName(TypeName typeName) {
        this.typeName = typeName;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Expression) {
            expr = (Expression) node;
        } else if (node instanceof TypeName) {
            typeName = (TypeName) node;
        }
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
