package raylras.zen.code.tree;

import raylras.zen.code.Range;
import raylras.zen.code.SourceUnit;
import raylras.zen.code.scope.LocalScope;
import raylras.zen.code.tree.stmt.Statement;

import java.util.List;

/**
 * Represents an Abstract Syntax Tree (AST) of the source unit,
 * which may contain imports, statements, and other elements.
 */
public class CompilationUnit extends TreeNode {

    public List<ImportDecl> imports;
    public List<ClassDecl> classes;
    public List<FunctionDecl> functions;
    public List<Statement> statements;
    public LocalScope localScope;
    public SourceUnit sourceUnit;

    public CompilationUnit(List<ImportDecl> imports, List<ClassDecl> classes, List<FunctionDecl> functions, List<Statement> statements, Range range) {
        super(range);
        this.imports = imports;
        this.classes = classes;
        this.functions = functions;
        this.statements = statements;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitCompilationUnit(this);
    }

}
