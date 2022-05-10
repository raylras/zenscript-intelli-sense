package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.verify.type.Type;

public abstract class Expression extends ASTNode {

    protected Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
