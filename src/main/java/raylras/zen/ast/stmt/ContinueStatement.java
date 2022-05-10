package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTVisitor;

public class ContinueStatement extends Statement {

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitContinueStatement(this);
    }

}
