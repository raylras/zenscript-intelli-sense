package raylras.zen.model.type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record FunctionType(Type returnType, List<Type> parameterTypes) implements Type {

    public FunctionType(Type returnType, Type... parameterTypes) {
        this(returnType, Arrays.asList(parameterTypes));
    }

    @Override
    public String getTypeName() {
        return "function" + parameterTypes.stream()
                .map(Type::getTypeName)
                .collect(Collectors.joining(",", "(", ")"))
                + returnType.getTypeName();
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
