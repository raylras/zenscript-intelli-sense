package raylras.zen.ast;

import org.antlr.v4.runtime.Token;

public class IDNode extends ASTNode {

    private String name;

    public IDNode(Token token) {
        this.name = token.getText();
        this.setSourcePosition(token);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
