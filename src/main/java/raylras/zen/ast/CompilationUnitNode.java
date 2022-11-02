package raylras.zen.ast;

import raylras.zen.ast.decl.FunctionDeclarationNode;
import raylras.zen.ast.decl.ImportDeclarationNode;
import raylras.zen.ast.decl.ZenClassDeclarationNode;
import raylras.zen.ast.stmt.StatementNode;

import java.util.List;

public class CompilationUnitNode extends ASTNode {

    private final List<ImportDeclarationNode> imports;
    private final List<FunctionDeclarationNode> functions;
    private final List<ZenClassDeclarationNode> zenClasses;
    private final List<StatementNode> statements;

    public CompilationUnitNode(List<ImportDeclarationNode> imports,
                               List<FunctionDeclarationNode> functions,
                               List<ZenClassDeclarationNode> zenClasses,
                               List<StatementNode> statements) {
        this.imports = imports;
        this.functions = functions;
        this.zenClasses = zenClasses;
        this.statements = statements;
    }

    public List<ImportDeclarationNode> getImports() {
        return imports;
    }

    public List<FunctionDeclarationNode> getFunctions() {
        return functions;
    }

    public List<ZenClassDeclarationNode> getZenClasses() {
        return zenClasses;
    }

    public List<StatementNode> getStatements() {
        return statements;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
