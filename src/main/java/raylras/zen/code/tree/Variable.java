package raylras.zen.code.tree;

import raylras.zen.code.tree.expr.Expression;

public interface Variable {

    Declarator getDeclarator();

    Name getName();

    TypeLiteral getTypeDecl();

    Expression getInit();

}
