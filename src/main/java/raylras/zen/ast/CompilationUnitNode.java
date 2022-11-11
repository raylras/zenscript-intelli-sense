package raylras.zen.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompilationUnitNode extends ASTNode {

    private List<ASTNode> children;

    public CompilationUnitNode() {
    }

    public List<ASTNode> getChildren() {
        return children == null ? Collections.emptyList() : children;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void addChild(ASTNode child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    @Override
    public String toString() {
        return children.stream().map(Objects::toString).collect(Collectors.joining("\n"));
    }

}
