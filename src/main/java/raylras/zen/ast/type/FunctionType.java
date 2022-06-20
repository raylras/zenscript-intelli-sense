package raylras.zen.ast.type;

import java.util.List;
import java.util.stream.Collectors;

public final class FunctionType implements Type {

    private final List<Type> arguments;
    private final Type result;

    public FunctionType(List<Type> arguments, Type result) {
        this.arguments = arguments;
        this.result = result;
    }

    public List<Type> getArguments() {
        return arguments;
    }

    public Type getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "function(" + arguments.stream().map(Object::toString).collect(Collectors.joining(",")) + ")" + result;
    }

}
