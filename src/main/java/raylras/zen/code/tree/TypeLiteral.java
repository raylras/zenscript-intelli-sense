package raylras.zen.code.tree;

import raylras.zen.code.Range;
import raylras.zen.code.type.Type;

/**
 * Represents a type literal such as "name", "function(type, ...)type", "type[]", "type[type]".
 * e.g. "int", "foo.bar.baz", "function(int,int)int", "int[]", "string[int]".
 */
public class TypeLiteral extends TreeNode {

    public Type type;

    public TypeLiteral(Type type, Range range) {
        super(range);
        this.type = type;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitTypeLiteral(this);
    }

}
