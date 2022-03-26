package raylras.zen.ast;

public class AliasNode extends ASTNode {

    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return alias;
    }

}
