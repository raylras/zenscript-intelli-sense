package raylras.zen.ast;

import raylras.zen.ast.type.Alias;
import raylras.zen.ast.type.Identifier;

public class AliasNode extends ASTNode implements Alias {

    private Identifier identifier;

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Identifier) {
            identifier = (Identifier) node;
        }
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}