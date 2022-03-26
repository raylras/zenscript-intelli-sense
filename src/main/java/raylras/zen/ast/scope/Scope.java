package raylras.zen.ast.scope;

import raylras.zen.ast.ASTNode;

public interface Scope<N extends ASTNode> {

    ASTNode resolve(String name);

    void define(String name, N node);

    Scope<?> getParent();

    void setParent(Scope<?> parent);

}
