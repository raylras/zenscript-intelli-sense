package raylras.zen.ast.decl;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.*;

import java.util.ArrayList;
import java.util.List;

/**
 * import a.b.C as d;
 */
public class ImportDeclarationNode extends ASTNode implements Declaration, Statement, TopLevel {

    private List<Identifier> packages;
    private Alias alias;

    public ImportDeclarationNode() {
    }

    public List<Identifier> getPackages() {
        return packages;
    }

    public void setPackages(List<Identifier> packages) {
        this.packages = packages;
    }

    public Alias getAlias() {
        return alias;
    }

    public void setAlias(Alias alias) {
        this.alias = alias;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Identifier) {
            if (packages == null) {
                packages = new ArrayList<>();
            }
            packages.add((Identifier) node);
        } else if (node instanceof Alias) {
            alias = (Alias) node;
        }
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
