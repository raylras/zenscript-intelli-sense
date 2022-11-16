package raylras.zen.ast.decl;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.*;
import raylras.zen.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * zenConstructor(a, b) { stmt; }
 */
public class ConstructorDeclarationNode extends ASTNode implements Constructor, Declaration {

    private List<Parameter> parameters;
    private List<Statement> statements;

    public ConstructorDeclarationNode() {
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
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
        } else if (node instanceof Statement) {
            if (statements == null) {
                statements = new ArrayList<>();
            }
            statements.add((Statement) node);
        }
    }

    @Override
    public List<Node> getChildren() {
        return CommonUtils.toChildrenList(parameters, statements);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
