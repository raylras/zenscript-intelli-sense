package raylras.zen.model.type;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record FunctionType(Type returnType, List<Type> parameterTypes) implements Type {

    public FunctionType(Type returnType, Type... parameterTypes) {
        this(returnType, Arrays.asList(parameterTypes));
    }

    @Override
    public String getTypeName() {
        return "function" + parameterTypes.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(",", "(", ")"))
                + returnType;
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
