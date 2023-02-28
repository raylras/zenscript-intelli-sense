package raylras.zen.code.tree;

import raylras.zen.code.tree.stmt.Statement;

import java.util.List;

public interface Function {

    Name getName();

    List<ParameterDecl> getParams();

    TypeLiteral getTypeDecl();

    List<Statement> getStatements();

}
