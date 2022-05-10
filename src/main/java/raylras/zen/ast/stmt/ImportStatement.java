package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTVisitor;
import raylras.zen.ast.ImportNode;

public class ImportStatement extends Statement {

    private final ImportNode importNode;

    public ImportStatement(ImportNode importNode) {
        this.importNode = importNode;
    }

    public ImportNode getImportNode() {
        return importNode;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitImportStatement(this);
        importNode.accept(visitor);
    }

}
