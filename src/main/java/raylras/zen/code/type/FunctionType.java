package raylras.zen.code.type;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FunctionType extends Type {

    public List<Type> paramTypes;
    public Type returnType;

    public FunctionType(List<Type> paramTypes, Type returnType) {
        this.paramTypes = paramTypes;
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        return "function" + paramTypes.stream().map(Objects::toString).collect(Collectors.joining(",", "(", ")")) + returnType;
    }

}
