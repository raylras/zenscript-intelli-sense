package raylras.zen.ast.type;

import java.util.Objects;

public abstract class Type {

    public String getTypeName() {
        return toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return Objects.equals(this.getTypeName(), type.getTypeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getTypeName());
    }

}
