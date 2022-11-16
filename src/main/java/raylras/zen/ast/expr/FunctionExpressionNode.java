package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.*;
import raylras.zen.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * function (a, b) { stmt; }
 */
public class FunctionExpressionNode extends ASTNode implements Function, Expression {

    private List<Parameter> parameters;
    private TypeAnnotation typeAnnotation;
    private List<Statement> statements;

    public FunctionExpressionNode() {
    }

    public Identifier getIdentifier() {
        return null;
    }

    public void setIdentifier(Identifier identifier) {
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public TypeAnnotation getTypeAnnotation() {
        return typeAnnotation;
    }

    public void setTypeAnnotation(TypeAnnotation typeAnnotation) {
        this.typeAnnotation = typeAnnotation;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Parameter) {
            if (parameters == null) {
                parameters = new ArrayList<>();
            }
            parameters.add((Parameter) node);
        } else if (node instanceof TypeAnnotation) {
            typeAnnotation = (TypeAnnotation) node;
        } else if (node instanceof Statement) {
            if (statements == null) {
                statements = new ArrayList<>();
            }
            statements.add((Statement) node);
        }
    }

    @Override
    public List<Node> getChildren() {
        return CommonUtils.toChildrenList(parameters, typeAnnotation, statements);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
