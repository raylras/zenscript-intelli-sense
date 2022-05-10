package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

import java.util.List;

public class ArgumentsExpression extends Expression {

    private final Expression left;
    private final List<Expression> arguments;

    public ArgumentsExpression(Expression left, List<Expression> arguments) {
        this.left = left;
        this.arguments = arguments;
    }

    public Expression getLeft() {
        return left;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitArgumentsExpression(this);
        left.accept(visitor);
        arguments.forEach(node -> node.accept(visitor));
    }

}
