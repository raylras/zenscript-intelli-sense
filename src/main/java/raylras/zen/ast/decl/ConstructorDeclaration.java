package raylras.zen.ast.decl;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.BlockNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ConstructorDeclaration extends BaseNode implements Declaration {

    @NotNull
    private final List<ParameterDeclaration> parameters;
    @NotNull
    private final BlockNode block;

    public ConstructorDeclaration(@NotNull List<ParameterDeclaration> parameters, @NotNull BlockNode block) {
        this.parameters = parameters;
        this.block = block;
    }

    @NotNull
    public List<ParameterDeclaration> getParameters() {
        return parameters;
    }

    @NotNull
    public BlockNode getBlock() {
        return block;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<Node> getChildren() {
        ArrayList<Node> children = new ArrayList<>(parameters.size() + 1);
        children.addAll(parameters);
        children.add(block);
        return Collections.unmodifiableList(children);
    }

    @Override
    public String toString() {
        return "zenConstructor(" + parameters.stream().map(Object::toString).collect(Collectors.joining(", ")) + ")" + " {...}";
    }

}
