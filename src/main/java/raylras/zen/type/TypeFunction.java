package raylras.zen.type;

import raylras.zen.lsp.antlr.ZenScriptParser;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// Function or Method
public class TypeFunction implements Type {

    private final String name;
    private final Type returnType;
    private final List<Type> argumentTypes;
    private final String signature;

    public TypeFunction(String name, Type returnType, List<Type> argumentTypes) {
        this.name = name;
        this.returnType = returnType;
        this.argumentTypes = argumentTypes;
        this.signature = getSignature(name, returnType, argumentTypes);
    }

    public TypeFunction(ZenScriptParser.TypeFunctionContext context) {
        this.name = context.FUNCTION().getText();
        List<ZenScriptParser.TypeContext> contexts = context.type();
        this.returnType = ZenType.getZenType(contexts.get(contexts.size() - 1));
        contexts.remove(contexts.size() - 1);
        this.argumentTypes = ZenType.getZenTypes(contexts);
        this.signature = getSignature();
    }

    public String getName() {
        return name;
    }

    public Type getReturnType() {
        return returnType;
    }

    public List<Type> getArgumentTypes() {
        return argumentTypes;
    }

    public String getSignature() {
        return signature;
    }

    public static String getSignature(String name, Type returnType, List<Type> argumentTypes) {
        StringBuilder builder = new StringBuilder();
        builder.append(name)
                .append('(')
                .append(argumentTypes == null ? "" : argumentTypes.stream().filter(Objects::nonNull).map(Type::getTypeName).collect(Collectors.joining(",")))
                .append(')')
                .append(returnType == null ? "" : returnType.getTypeName());
        return builder.toString();
    }

    @Override
    public String toString() {
        return signature;
    }

}
