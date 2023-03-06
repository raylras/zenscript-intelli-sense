package raylras.zen.code.tree;

import raylras.zen.code.Range;
import raylras.zen.code.type.Type;

/**
 * Represents a type literal such as "name", "function(type, ...)type", "type[]", "type[type]".
 * e.g. "int", "foo.bar.baz", "function(int,int)int", "int[]", "string[int]".
 */
public class TypeLiteral extends TreeNode {

    public String literal;
    public Type type;

    public TypeLiteral(String literal, Type type, Range range) {
        super(range);
        this.literal = literal;
        this.type = type;
    }

    @Override
    public <R> R accept(TreeVisitor<R> visitor) {
        return visitor.visitTypeLiteral(this);
    }

    @Override
    public String toString() {
        return new Pretty().visitTypeLiteral(this);
    }

}
