package raylras.zen.ast.decl;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.BlockNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * zenConstructor(a, b) { stmt; }
 */
public class ConstructorDeclarationNode extends ASTNode implements DeclarationNode {

    private final List<ParameterDeclarationNode> parameters;
    private final BlockNode block;

    public ConstructorDeclarationNode(List<ParameterDeclarationNode> parameters, BlockNode block) {
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
        return "zenConstructor(" + parameters.stream().map(Object::toString).collect(Collectors.joining(", ")) + ")" + " {...}";
    }

}
