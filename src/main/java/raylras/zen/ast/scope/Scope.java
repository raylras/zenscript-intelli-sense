package raylras.zen.ast.scope;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.stmt.Statement;

public interface Scope {

    Scope getParent();

    void setParent(Scope parent);

    ASTNode resolve(String name);

    void define(String name, ASTNode node);

    void addStatement(Statement statement);

}
