package raylras.zen.verify;

import raylras.zen.Environment;
import raylras.zen.ast.*;
import raylras.zen.ast.scope.Scope;
import raylras.zen.ast.scope.StaticScope;
import raylras.zen.ast.stmt.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// TODO: static verify
public class StaticVerifier implements ASTVisitor<ErrorNode> {

    private final Environment env;
    private final Stack<Scope<? extends ASTNode>> scopeStack = new Stack<>();
    private final List<ErrorNode> errors = new ArrayList<>();

    private Scope<ASTNode> statics; // functions and static variables in script

    public StaticVerifier(Environment env) {
        this.env = env;
    }

    public List<ErrorNode> getErrors() {
        return errors;
    }

    private void pushScope(Scope<? extends ASTNode> scope) {
        scopeStack.push(scope);
    }

    private void popScope() {
        scopeStack.pop();
    }

    @Override
    public ErrorNode visitScript(ScriptNode script) {
        statics = new StaticScope();
        scopeStack.push(statics);
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
