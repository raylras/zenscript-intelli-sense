package raylras.zen.util;

import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.Type;
import raylras.zen.code.resolve.ExpressionTypeResolver;

import java.util.List;
import java.util.stream.Collectors;

public class TypeUtils {
    public static boolean isValidType(Type type) {
        return type != null && type.getKind() != Type.Kind.NONE;
    }


    public static FunctionType extractFunctionType(Type type) {
        if (type instanceof FunctionType) {
            return (FunctionType) type;
        }
        if (type instanceof ClassType) {
            ClassSymbol classSymbol = ((ClassType) type).getSymbol();
            if (classSymbol.isFunctionalInterface()) {
                return classSymbol.getFunctionType();
            }
        }
        return null;
    }


    public static List<Type> getArgumentTypes(ExpressionTypeResolver resolver, ZenScriptParser.CallExprContext ctx) {
        return ctx.expression().stream()
            .skip(1)
            .map(it -> it.accept(resolver))
            .collect(Collectors.toList());
    }
}
