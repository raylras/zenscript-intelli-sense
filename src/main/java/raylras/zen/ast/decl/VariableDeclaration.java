package raylras.zen.ast.decl;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.*;
import raylras.zen.ast.stmt.VariableDeclStatement;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.List;

/**
 * This is not a statement. It is the variable declaration of the foreach statement.
 * @see VariableDeclStatement
 */
public class VariableDeclaration extends BaseNode implements Declaration, HasID {

    @NotNull
    private final IDNode id;

    public VariableDeclaration(@NotNull IDNode id) {
        this.id = id;
    }

    @NotNull
    @Override
    public IDNode getId() {
        return id;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return List.of(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
