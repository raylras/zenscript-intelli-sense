package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTVisitor;

public class BreakStatement extends Statement {

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitBreakStatement(this);
    }

}
