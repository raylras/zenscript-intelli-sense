package raylras.zen.code.tree;

import raylras.zen.code.tree.expr.Expression;

public interface Variable {

    Declarator getDeclarator();

    SimpleName getSimpleName();

    TypeLiteral getTypeDecl();

    Expression getInit();

}
