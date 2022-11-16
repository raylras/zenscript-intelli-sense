package raylras.zen.ast.decl;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.*;
import raylras.zen.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * function fn(a, b) as int { stmt; }
 */
public class FunctionDeclarationNode extends ASTNode implements Function, Declaration, TopLevel {

    private Identifier identifier;
    private List<Parameter> parameters;
    private TypeAnnotation typeAnnotation;
    private List<Statement> statements;

    public FunctionDeclarationNode() {
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
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
        if (node instanceof Identifier) {
            identifier = (Identifier) node;
        } else if (node instanceof Parameter) {
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
        return CommonUtils.toChildrenList(identifier, parameters, typeAnnotation, statements);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
