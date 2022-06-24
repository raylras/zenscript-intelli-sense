package raylras.zen.ast.type;

public record ErrorType(String message) implements Type {

    @Override
    public String toString() {
        return "ErrorType{" + message + "}";
    }

}
