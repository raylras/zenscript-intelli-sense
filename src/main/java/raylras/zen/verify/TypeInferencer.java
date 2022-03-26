package raylras.zen.verify;

import raylras.zen.ast.*;
import raylras.zen.ast.stmt.Statement;

// TODO: type inference
public class TypeInferencer implements ASTVisitor<ErrorNode> {

    @Override
    public ErrorNode visitScript(ScriptNode script) {
        return null;
    }

    @Override
    public ErrorNode visitImport(ImportNode importNode) {
        return null;
    }

    @Override
    public ErrorNode visitReference(ReferenceNode reference) {
        return null;
    }

    @Override
    public ErrorNode visitAlias(AliasNode alias) {
        return null;
    }

    @Override
    public ErrorNode visitFunction(FunctionNode function) {
        return null;
    }

    @Override
    public ErrorNode visitParameter(ParameterNode parameter) {
        return null;
    }

    @Override
    public ErrorNode visitZenClass(ZenClassNode zenClass) {
        return null;
    }

    @Override
    public ErrorNode visitBlock(BlockNode block) {
        return null;
    }

    @Override
    public ErrorNode visitStatement(Statement statement) {
        return null;
    }

    @Override
    public ErrorNode visitType(TypeNode type) {
        return null;
    }

    @Override
    public ErrorNode visitID(IDNode id) {
        return null;
    }

}
