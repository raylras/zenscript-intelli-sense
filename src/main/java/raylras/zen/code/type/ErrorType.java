package raylras.zen.code.type;

public class ErrorType extends Type{
    @Override
    public Kind getKind() {
        return Kind.NONE;
    }

    @Override
    public String toString() {
        return "ERROR";
    }
}
