package raylras.zen.ast.decl;

import raylras.zen.ast.*;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.IdentifierNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * function fn(a, b) as int { stmt; }
 */
public final class FunctionDeclarationNode extends ASTNode implements DeclarationNode, TopLevelNode {

    private final IdentifierNode identifier;
    private final List<ParameterDeclarationNode> parameters;
    private final TypeAnnotationNode typeAnnotation;
    private final BlockNode block;

    public FunctionDeclarationNode(IdentifierNode identifier,
                                   List<ParameterDeclarationNode> parameters,
                                   TypeAnnotationNode typeAnnotation,
                                   BlockNode block) {
        this.identifier = identifier;
        this.parameters = parameters;
        this.typeAnnotation = typeAnnotation;
        this.block = block;
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public List<ParameterDeclarationNode> getParameters() {
        return parameters;
    }

    public Optional<TypeAnnotationNode> getTypeAnnotation() {
        return Optional.ofNullable(typeAnnotation);
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
        StringBuilder builder = new StringBuilder();
        builder.append("function ").append(identifier);
        builder.append("(");
        builder.append(parameters.stream().map(Object::toString).collect(Collectors.joining(", ")));
        builder.append(")");
        builder.append(" {...}");
        return builder.toString();
    }

}
