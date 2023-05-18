package raylras.zen.code.type;

public abstract class NumberType extends ClassType {

    protected NumberType(String name) {
        super(name);
    }

    @Override
    public Kind getKind() {
        return Kind.NUMBER;
    }

}
