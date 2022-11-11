package raylras.zen.ast.decl;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.BlockNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * zenConstructor(a, b) { stmt; }
 */
public class ConstructorDeclarationNode extends ASTNode implements DeclarationNode {

    private List<ParameterDeclarationNode> parameters;
    private BlockNode block;

    public ConstructorDeclarationNode() {;
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
    public void addChild(ASTNode node) {
        if (node.getClass() == ParameterDeclarationNode.class) {
            if (parameters == null) {
                parameters = new ArrayList<>();
            }
            parameters.add((ParameterDeclarationNode) node);
        } else if (node.getClass() == BlockNode.class) {
            if (block == null) {
                block = (BlockNode) node;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("zenConstructor");
        builder.append("(");
        builder.append(parameters.stream().map(Object::toString).collect(Collectors.joining(", ")));
        builder.append(")");
        builder.append(" ");
        builder.append(block);
        return builder.toString();
    }

}
