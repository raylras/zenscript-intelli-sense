package raylras.zen.ast.type;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// Function or Method
public class TypeFunction extends Type {

    private Type returnType;
    private List<Type> parameterTypes;

    public TypeFunction() {}

    public TypeFunction(Type returnType, Type... parameterTypes) {
        this(returnType, Arrays.asList(parameterTypes));
    }

    public TypeFunction(Type returnType, List<Type> parameterTypes) {
        if (returnType == null) {
            System.err.println("The return type should not be null! Fallback to TypeVoid");
            returnType = TypeVoid.INSTANCE;
        }
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public List<Type> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(List<Type> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    @Override
    public String toString() {
        return getTypeName(this);
    }

    public static String getTypeName(TypeFunction type) {
        return getTypeName(type.returnType, type.parameterTypes);
    }

    public static String getTypeName(Type returnType, List<Type> parameterTypes) {
        StringBuilder builder = new StringBuilder();
        builder.append("function")
                .append('(')
                .append(parameterTypes == null ? "" : parameterTypes.stream().filter(Objects::nonNull).map(Type::getTypeName).collect(Collectors.joining(",")))
                .append(')')
                .append(returnType.getTypeName());
        return builder.toString();
    }

}
