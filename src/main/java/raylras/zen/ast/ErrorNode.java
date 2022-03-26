package raylras.zen.ast;

import raylras.zen.verify.EnumError;

public class ErrorNode extends ASTNode {

    private ASTNode errorNode;
    private ASTNode causeNode;
    private EnumError error;

    public ErrorNode() {}

    public ErrorNode(ASTNode errorNode) {
        this(errorNode, null, null);
    }

    public ErrorNode(ASTNode errorNode, EnumError error) {
        this(errorNode, null, error);
    }

    public ErrorNode(ASTNode errorNode, ASTNode causeNode, EnumError error) {
        this.errorNode = errorNode;
        this.causeNode = causeNode;
        this.error = error;
        this.setSourcePosition(errorNode);
    }

    public ASTNode getErrorNode() {
        return errorNode;
    }

    public void setErrorNode(ASTNode errorNode) {
        this.errorNode = errorNode;
    }

    public ASTNode getCauseNode() {
        return causeNode;
    }

    public void setCauseNode(ASTNode causeNode) {
        this.causeNode = causeNode;
    }

    public EnumError getError() {
        return error;
    }

    public void setError(EnumError error) {
        this.error = error;
    }

}
