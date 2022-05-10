package raylras.zen.ast;

import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.expr.Expression;
import raylras.zen.verify.type.AbstractType;
import raylras.zen.verify.type.Type;


public class FieldNode extends ASTNode {

    private final IdentifierNode idNode;
    @Nullable
    private final TypeNode typeNode;

    @Nullable
    private final Expression exprNode;

    private Type type;
    private boolean isFinal;

    public FieldNode(IdentifierNode idNode, @Nullable TypeNode typeNode, @Nullable Expression exprNode) {
        this.idNode = idNode;
        this.typeNode = typeNode;
        this.exprNode = exprNode;
    }

    public IdentifierNode getIdNode() {
        return idNode;
    }

    @Nullable
    public TypeNode getTypeNode() {
        return typeNode;
    }

    @Nullable
    public Expression getExprNode() {
        return exprNode;
    }

    public Type getType() {
        return type;
    }

    public void setType(AbstractType type) {
        this.type = type;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitField(this);
        idNode.accept(visitor);
        if (typeNode != null) {
            typeNode.accept(visitor);
        }
        if (exprNode != null) {
            exprNode.accept(visitor);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(idNode);
        if (type != null) {
            builder.append(" as ").append(type);
        }
        return builder.toString();
    }

}
