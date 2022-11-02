package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

/**
 * &lt;item:dirt&gt;
 */
public class BracketHandlerExpressionNode extends ASTNode implements ExpressionNode {

    private final String content;

    public BracketHandlerExpressionNode(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "<" + content + ">";
    }

}
