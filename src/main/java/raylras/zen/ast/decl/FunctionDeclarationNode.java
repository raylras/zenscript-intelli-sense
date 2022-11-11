package raylras.zen.ast.decl;

import raylras.zen.ast.*;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.IdentifierNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * function fn(a, b) as int { stmt; }
 */
public class FunctionDeclarationNode extends ASTNode implements DeclarationNode, TopLevelNode {

    private IdentifierNode identifier;
    private List<ParameterDeclarationNode> parameters;
    private TypeAnnotationNode typeAnnotation;
    private BlockNode block;

    public FunctionDeclarationNode() {
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public List<ParameterDeclarationNode> getParameters() {
        return parameters == null ? Collections.emptyList() : parameters;
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
    public void addChild(ASTNode node) {
        Class<? extends ASTNode> clazz = node.getClass();
        if (clazz == IdentifierNode.class) {
            if (identifier == null) {
                identifier = (IdentifierNode) node;
            }
        } else if (clazz == ParameterDeclarationNode.class) {
            if (parameters == null) {
                parameters = new ArrayList<>();
            }
            parameters.add((ParameterDeclarationNode) node);
        } else if (clazz == TypeAnnotationNode.class) {
            if (typeAnnotation == null) {
                typeAnnotation = (TypeAnnotationNode) node;
            }
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
        builder.append(" ");
        builder.append(identifier);
        builder.append("(");
        builder.append(getParameters().stream().map(Object::toString).collect(Collectors.joining(", ")));
        builder.append(")");
        if (typeAnnotation != null) {
            builder.append(" as ");
            builder.append(typeAnnotation);
        }
        builder.append(" ");
        builder.append(block);
        return builder.toString();
    }

}
