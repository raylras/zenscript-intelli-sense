package raylras.zen.ast.type;

public final class ErrorType implements Type {
    private final String message;

    public ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorType{" + message +"}";
    }

}
