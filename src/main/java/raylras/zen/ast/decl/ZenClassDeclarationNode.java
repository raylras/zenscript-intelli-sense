package raylras.zen.ast.decl;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.IdentifierNode;
import raylras.zen.ast.TopLevelNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * zenClass MyClass { var a; zenConstructor(){ stmt; } function fn(){ stmt;} }
 */
public class ZenClassDeclarationNode extends ASTNode implements DeclarationNode, TopLevelNode {

    private IdentifierNode identifier;
    private List<ASTNode> children;

    public ZenClassDeclarationNode() {
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public List<ASTNode> getChildren() {
        return children == null ? Collections.emptyList() : children;
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
        } else if (clazz == VariableDeclarationNode.class) {
            add(node);
        } else if (clazz == ConstructorDeclarationNode.class) {
            add(node);
        } else if (clazz == FunctionDeclarationNode.class) {
            add(node);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("zenClass");
        builder.append(" ");
        builder.append(identifier);
        builder.append(" ");
        builder.append("{");
        builder.append("\n");
        builder.append(children.stream().map(Objects::toString).collect(Collectors.joining("\n")));
        builder.append("}");
        return builder.toString();
    }

    private void add(ASTNode node) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(node);
    }

}
