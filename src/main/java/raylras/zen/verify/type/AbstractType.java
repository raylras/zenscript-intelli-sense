package raylras.zen.verify.type;

import java.util.Objects;

public abstract class AbstractType implements Type {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(this.toString(), o.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.toString());
    }

}
