package raylras.zen.semantic.type;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FunctionType implements Type {

    private List<Type> paramTypes;
    private Type returnType;

    public FunctionType(List<Type> paramTypes, Type returnType) {
        Objects.requireNonNull(paramTypes);
        Objects.requireNonNull(returnType);
        this.paramTypes = paramTypes;
        this.returnType = returnType;
    }

    public List<Type> getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(List<Type> paramTypes) {
        Objects.requireNonNull(paramTypes);
        this.paramTypes = paramTypes;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        Objects.requireNonNull(returnType);
        this.returnType = returnType;
    }

    @Override
    public String typeName() {
        return "function(" + paramTypes.stream().map(Type::typeName).collect(Collectors.joining(",")) + ")" + returnType.typeName();
    }

    @Override
    public boolean isType(Type type) {
        if (this == type) {
            return true;
        }
        if (!(type instanceof FunctionType)) {
            return false;
        }
        FunctionType that = (FunctionType) type;
        if (!this.getReturnType().isType(that.getReturnType())) {
            return false;
        }
        List<Type> thisParamTypes = this.getParamTypes();
        List<Type> thatParamTypes = that.getParamTypes();
        if (thisParamTypes.size() != thatParamTypes.size()) {
            return false;
        }
        for (int i = 0; i < thisParamTypes.size(); i++) {
            if (!thisParamTypes.get(i).isType(thatParamTypes.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "(type (function (" + paramTypes.stream().map(Type::typeName).collect(Collectors.joining(" ")) + ") "  + returnType.typeName() + "))";
    }

}
