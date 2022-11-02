package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.BlockNode;
import raylras.zen.ast.decl.ParameterDeclarationNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.List;

/**
 * function (a, b) { stmt; }
 */
public class FunctionExpressionNode extends ASTNode implements ExpressionNode {

    private final List<ParameterDeclarationNode> parameters;
    private final BlockNode block;

    public FunctionExpressionNode(List<ParameterDeclarationNode> parameters, BlockNode block) {
        this.parameters = parameters;
        this.block = block;
    }

    public List<ParameterDeclarationNode> getParameters() {
        return parameters;
    }

    public BlockNode getBlock() {
        return block;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "function";
    }

}
