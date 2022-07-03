package raylras.zen.ast;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.type.Type;

public abstract class BaseNode implements Node {

    private Range range;
    private Type type;

    @Override
    public Range getRange() {
        return range;
    }

    @Override
    public void setRange(Range range) {
        this.range = range;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public int compareTo(@NotNull Node other) {
        return this.range.compareTo(other.getRange());
    }

}
