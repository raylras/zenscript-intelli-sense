package raylras.zen.ast;

import raylras.zen.ast.stmt.Statement;

@SuppressWarnings("UnusedReturnValue")
public interface ASTVisitor<T> {

    T visitScript(ScriptNode script);

    T visitImport(ImportNode importNode);

    T visitReference(ReferenceNode reference);

    T visitAlias(AliasNode alias);

    T visitFunction(FunctionNode function);

    T visitParameter(ParameterNode parameter);

    T visitZenClass(ZenClassNode zenClass);

    T visitBlock(BlockNode block);

    T visitStatement(Statement statement);

    T visitType(TypeNode type);

    T visitID(IDNode id);

}
