package raylras.zen.ast.decl;

import raylras.zen.ast.*;
import raylras.zen.ast.IdentifierNode;
import raylras.zen.ast.stmt.StatementNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.List;
import java.util.Optional;

/**
 * import a.b.C as d;
 */
public class ImportDeclarationNode extends ASTNode implements DeclarationNode, StatementNode, TopLevelNode {

    private final List<IdentifierNode> packages;
    private final IdentifierNode alias;

    public ImportDeclarationNode(List<IdentifierNode> packages, IdentifierNode alias) {
        this.packages = packages;
        this.alias = alias;
    }

    public List<IdentifierNode> getPackages() {
        return packages;
    }

    public Optional<IdentifierNode> getAlias() {
        return Optional.ofNullable(alias);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "import";
    }

}
