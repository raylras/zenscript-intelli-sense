package raylras.zen.ast.decl;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.LocatableID;
import raylras.zen.ast.Node;
import raylras.zen.ast.Range;
import raylras.zen.ast.stmt.VariableDeclStatement;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collections;
import java.util.List;

/**
 * This is not a statement. It is the variable declaration of the foreach statement.
 * @see VariableDeclStatement
 */
public class VariableDeclaration extends BaseNode implements Declaration, LocatableID {

    @NotNull
    private final String name;

    public VariableDeclaration(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return List.of();
    }

    @Override
    public Range getIdRange() {
        return this.getRange();
    }

    @Override
    public String toString() {
        return name;
    }

}
