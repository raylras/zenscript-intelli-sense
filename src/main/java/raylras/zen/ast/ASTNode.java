package raylras.zen.ast;

import org.antlr.v4.runtime.ParserRuleContext;

public abstract class ASTNode implements Node {

    protected ASTNode parent;
    protected NodeLocation location;
//    protected Type type;
    protected ParserRuleContext internalContext;

    @Override
    public NodeLocation getLocation() {
        return location;
    }

    @Override
    public void setLocation(NodeLocation location) {
        this.location = location;
    }

}
