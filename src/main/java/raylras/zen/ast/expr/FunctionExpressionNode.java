package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.BlockNode;
import raylras.zen.ast.decl.ParameterDeclarationNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * function (a, b) { stmt; }
 */
public class FunctionExpressionNode extends ASTNode implements ExpressionNode {

    private List<ParameterDeclarationNode> parameters;
    private BlockNode block;

    public FunctionExpressionNode() {
    }

    public List<ParameterDeclarationNode> getParameters() {
        return parameters == null ? Collections.emptyList() : parameters;
    }

    public BlockNode getBlock() {
        return block;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void addChild(ASTNode node) {
        Class<? extends ASTNode> clazz = node.getClass();
        if (clazz == ParameterDeclarationNode.class) {
            if (parameters == null) {
                parameters = new ArrayList<>();
            }
            parameters.add((ParameterDeclarationNode) node);
        } else if (clazz == BlockNode.class) {
            if (block == null) {
                block = (BlockNode) node;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("function");
        builder.append("(");
        builder.append(parameters.stream().map(Objects::toString).collect(Collectors.joining(", ")));
        builder.append(")");
        builder.append(block);
        return builder.toString();
    }

}
