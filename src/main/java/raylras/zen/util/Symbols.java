package raylras.zen.util;

import raylras.zen.code.symbol.FunctionSymbol;
import raylras.zen.code.symbol.ParameterSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.Type;

import java.util.List;
import java.util.stream.Collectors;

public class Symbols {

    public static <T extends Symbol> List<T> getMembersByName(Type type, String simpleName, Class<T> clazz) {
        return type.getMembers().stream()
                .filter(symbol -> symbol.getSimpleName().equals(simpleName))
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

    public static boolean match(FunctionSymbol function, List<Type> argumentTypeList) {
        List<ParameterSymbol> parameterList = function.getParameterList();
        for (int i = 0; i < parameterList.size(); i++) {
            ParameterSymbol parameter = parameterList.get(i);
            if (i < argumentTypeList.size()) {
                Type argument = argumentTypeList.get(i);
                if (!argument.isAssignableTo(parameter.getType())) {
                    return false;
                }
            } else if (!parameter.isOptional()) {
                return false;
            }
        }
        return true;
    }

}
