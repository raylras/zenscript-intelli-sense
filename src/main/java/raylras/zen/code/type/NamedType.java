package raylras.zen.code.type;

public abstract class NamedType extends Type {

    public String getName() {
        return name;
    }

    protected final String name;

    protected NamedType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
