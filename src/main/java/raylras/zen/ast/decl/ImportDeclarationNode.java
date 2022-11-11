package raylras.zen.ast.decl;

import raylras.zen.ast.*;
import raylras.zen.ast.IdentifierNode;
import raylras.zen.ast.stmt.StatementNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * import a.b.C as d;
 */
public class ImportDeclarationNode extends ASTNode implements DeclarationNode, StatementNode, TopLevelNode {

    private List<IdentifierNode> packages;
    private AliasNode alias;

    public ImportDeclarationNode() {
    }

    public List<IdentifierNode> getPackages() {
        return packages == null ? Collections.emptyList() : packages;
    }

    public Optional<AliasNode> getAlias() {
        return Optional.ofNullable(alias);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void addChild(ASTNode node) {
        Class<? extends ASTNode> clazz = node.getClass();
        if (clazz == IdentifierNode.class) {
            if (packages == null) {
                packages = new ArrayList<>();
            }
            packages.add((IdentifierNode) node);
        } else if (clazz == AliasNode.class) {
            if (alias == null) {
                alias = (AliasNode) node;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("import");
        builder.append(" ");
        builder.append(getPackages().stream().map(IdentifierNode::getValue).collect(Collectors.joining(".")));
        if (alias != null) {
            builder.append(" as ");
            builder.append(alias);
        }
        builder.append(";");
        return builder.toString();
    }

}
