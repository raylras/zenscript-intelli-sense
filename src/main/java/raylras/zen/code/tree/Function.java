package raylras.zen.code.tree;

import raylras.zen.code.tree.stmt.Statement;

import java.util.List;

public interface Function {

    SimpleName getSimpleName();

    List<ParameterDeclaration> getParams();

    TypeLiteral getTypeDecl();

    List<Statement> getStatements();

}
