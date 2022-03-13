package raylras.zen.ast;

public class ErrorNode extends ASTNode {

    private final ASTNode errorNode;
    private EnumError error;

    public ErrorNode(ASTNode errorNode) {
        this(errorNode, EnumError.UNKNOWN);
    }

    public ErrorNode(ASTNode errorNode, EnumError error) {
        this.errorNode = errorNode;
        this.error = error;
        this.setSourcePosition(errorNode);
    }

    public ASTNode getErrorNode() {
        return errorNode;
    }

    public EnumError getError() {
        return error;
    }

    public void setError(EnumError error) {
        this.error = error;
        this.setSourcePosition(errorNode);
    }

}
