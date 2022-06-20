package raylras.zen.ast.decl;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.LocatableID;
import raylras.zen.ast.Node;
import raylras.zen.ast.Range;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collections;
import java.util.List;

public final class AliasDeclaration extends BaseNode implements Declaration, LocatableID {

    @NotNull
    private final String name;

    private Range idRange;

    public AliasDeclaration(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @Override
    public Range getIdRange() {
        return idRange;
    }

    public void setIdRange(Range idRange) {
        this.idRange = idRange;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<Node> getChildren() {
        return Collections.emptyList();
    }


    @Override
    public String toString() {
        return name;
    }

}
