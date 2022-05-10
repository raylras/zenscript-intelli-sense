package raylras.zen.ast;

import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.expr.Expression;
import raylras.zen.verify.type.Type;

public class ParameterNode extends ASTNode {

    private final IdentifierNode idNode;
    @Nullable
    private final TypeNode typeNode;
    @Nullable
    private final Expression defaultValueExpr;
    private Type type;

    public ParameterNode(IdentifierNode idNode, @Nullable TypeNode typeNode, @Nullable Expression defaultValueExpr) {
        this.idNode = idNode;
        this.typeNode = typeNode;
        this.defaultValueExpr = defaultValueExpr;
    }

    public IdentifierNode getIdNode() {
        return idNode;
    }

    @Nullable
    public TypeNode getTypeNode() {
        return typeNode;
    }

    @Nullable
    public Expression getDefaultValueExpr() {
        return defaultValueExpr;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitParameter(this);
        idNode.accept(visitor);
        if (typeNode != null) {
            typeNode.accept(visitor);
        }
        if (defaultValueExpr != null) {
            defaultValueExpr.accept(visitor);
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
