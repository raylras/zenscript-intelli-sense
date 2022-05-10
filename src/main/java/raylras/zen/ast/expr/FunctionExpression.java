package raylras.zen.ast.expr;

import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.ASTVisitor;
import raylras.zen.ast.BlockNode;
import raylras.zen.ast.ParameterNode;
import raylras.zen.ast.TypeNode;

import java.util.List;

public class FunctionExpression extends Expression {

    private final List<ParameterNode> parameterNodeList;
    @Nullable
    private final TypeNode resultTypeNode;
    private final BlockNode blockNode;

    public FunctionExpression(List<ParameterNode> parameterNodeList, @Nullable TypeNode resultTypeNode, BlockNode blockNode) {
        this.parameterNodeList = parameterNodeList;
        this.resultTypeNode = resultTypeNode;
        this.blockNode = blockNode;
    }

    public List<ParameterNode> getParameterNodeList() {
        return parameterNodeList;
    }

    @Nullable
    public TypeNode getResultTypeNode() {
        return resultTypeNode;
    }

    public BlockNode getBlockNode() {
        return blockNode;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitFunctionExpression(this);
        parameterNodeList.forEach(node -> node.accept(visitor));
        if (resultTypeNode != null) {
            resultTypeNode.accept(visitor);
        }
        blockNode.accept(visitor);
    }

}
