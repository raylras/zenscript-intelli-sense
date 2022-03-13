package raylras.zen.ast;

import raylras.zen.ast.stmt.Statement;
import raylras.zen.ast.type.Type;
import raylras.zen.ast.type.TypeFunction;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionNode extends ASTNode {

    private String name;
    private List<VariableNode> parameters;
    private List<Statement> statements;
    private TypeFunction type;

    public FunctionNode(String name) {
        this.name = name;
        this.type = new TypeFunction();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VariableNode> getParameters() {
        return parameters;
    }

    public void setParameters(List<VariableNode> parameters) {
        this.parameters = parameters;
        this.type.setParameterTypes(parameters.stream().map(VariableNode::getType).collect(Collectors.toList()));
    }

    public Type getReturnType() {
        return this.type.getReturnType();
    }

    public void setReturnType(Type returnType) {
        this.type.setReturnType(returnType);
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("function ")
                .append(name)
                .append("(")
                .append(parameters.stream().map(VariableNode::getNameAndType).collect(Collectors.joining(",")))
                .append(")")
                .append(" as ")
                .append(type.getReturnType());
        return builder.toString();
    }

}
