package raylras.zen.code.tree.stmt;

import raylras.zen.code.Range;
import raylras.zen.code.scope.LocalScope;
import raylras.zen.code.tree.Pretty;
import raylras.zen.code.tree.TreeVisitor;

import java.util.List;

/**
 * Represents a block statement such as "{ statement, ... }".
 * e.g. "{ i++; return j; }".
 */
public class Block extends Statement {

    public List<Statement> statements;
    public LocalScope localScope;

    public Block(List<Statement> statements, Range range) {
        super(range);
        this.statements = statements;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitBlock(this);
    }

    @Override
    public String toString() {
        return new Pretty().visitBlock(this);
    }

}
