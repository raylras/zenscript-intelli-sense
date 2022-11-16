package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.*;
import raylras.zen.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * for a, b in expr { stmt; }
 */
public class ForeachStatementNode extends ASTNode implements Statement, TopLevel {

    private List<Variable> variables;
    private Expression expr;
    private List<Statement> statements;

    public ForeachStatementNode() {
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Variable) {
            if (variables == null) {
                variables = new ArrayList<>();
            }
            variables.add((Variable) node);
        } else if (node instanceof Expression) {
            expr = (Expression) node;
        } else if (node instanceof Statement) {
            if (statements == null) {
                statements = new ArrayList<>();
            }
            statements.add((Statement) node);
        }
    }

    @Override
    public List<Node> getChildren() {
        return CommonUtils.toChildrenList(variables, expr, statements);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
