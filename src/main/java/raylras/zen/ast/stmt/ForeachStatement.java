package raylras.zen.ast.stmt;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.BlockNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.decl.VariableDeclaration;
import raylras.zen.ast.expr.Expression;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ForeachStatement extends BaseNode implements Statement {

    @NotNull
    private final List<VariableDeclaration> variables;
    @NotNull
    private final Expression expr;
    @NotNull
    private final BlockNode block;

    public ForeachStatement(@NotNull List<VariableDeclaration> variables, @NotNull Expression expr, @NotNull BlockNode block) {
        this.variables = variables;
        this.expr = expr;
        this.block = block;
    }

    @NotNull
    public List<VariableDeclaration> getVariables() {
        return variables;
    }

    @NotNull
    public Expression getExpr() {
        return expr;
    }

    @NotNull
    public BlockNode getBlock() {
        return block;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Stream.concat(
                variables.stream(),
                Stream.of(expr, block)
        ).toList();
    }

    @Override
    public String toString() {
        return "for " + variables.stream().map(Object::toString).collect(Collectors.joining(", ")) + " in " + expr + " {...}";
    }

}
