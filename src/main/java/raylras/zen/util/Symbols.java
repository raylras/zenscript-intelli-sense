package raylras.zen.util;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.symbol.*;
import raylras.zen.code.type.SubtypeResult;
import raylras.zen.code.type.Type;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Symbols {

    public static String getQualifiedName(Symbol symbol) {
        String declaredName = symbol.getName();
        String packageName = symbol.getUnit().getPackage();
        return packageName.isEmpty()
                ? declaredName
                : packageName + "." + PathUtils.getFileNameWithoutSuffix(symbol.getUnit().getPath()) + "." + declaredName;
    }

    public static Stream<Symbol> getToplevelSymbolsSpecial(CompilationUnit unit) {
        return unit.getTopLevelSymbols().stream()
                .filter(symbol -> !(symbol instanceof ImportSymbol))
                .flatMap(symbol -> {
                    if (PathUtils.isDzsFile(symbol.getUnit().getPath()) && symbol instanceof ClassSymbol) {
                        return ((ClassSymbol) symbol).getMembers().stream();
                    } else {
                        return Stream.of(symbol);
                    }
                })
                .filter(symbol -> symbol.isModifiedBy(Symbol.Modifier.STATIC) || symbol.isModifiedBy(Symbol.Modifier.GLOBAL));
    }

    public static <T extends Symbol> List<T> getMembersByName(Type type, String simpleName, Class<T> clazz) {
        return type.getMembers().stream()
                .filter(symbol -> symbol.getName().equals(simpleName))
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

    public static boolean areArgumentsMatch(FunctionSymbol function, List<Type> argumentTypeList) {
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

    public static FunctionSymbol findBestMatch(List<FunctionSymbol> functions, List<Type> argumentTypeList) {
        FunctionSymbol found = null;
        SubtypeResult foundMatchingResult = SubtypeResult.MISMATCH;
        for (FunctionSymbol function : functions) {
            List<ParameterSymbol> parameterList = function.getParameterList();
            SubtypeResult functionMatchingResult = SubtypeResult.SELF;
            if (argumentTypeList.size() > parameterList.size()) {
                continue;
            }
            for (int i = 0; i < parameterList.size(); i++) {
                if (i < argumentTypeList.size()) {
                    Type argType = argumentTypeList.get(i);
                    Type paramType = parameterList.get(i).getType();
                    functionMatchingResult = SubtypeResult.higher(functionMatchingResult, argType.isSubtypeOf(paramType));
                } else {
                    functionMatchingResult = SubtypeResult.higher(functionMatchingResult, parameterList.get(i).isOptional() ? SubtypeResult.SELF : SubtypeResult.MISMATCH);
                }
            }
            if (functionMatchingResult.priority < foundMatchingResult.priority) {
                found = function;
                foundMatchingResult = functionMatchingResult;
            }
        }
        return found;
    }

    public static Type predictNextArgumentType(List<FunctionSymbol> functions, List<Type> argumentTypes) {
        Type found = null;
        SubtypeResult foundMatchingResult = SubtypeResult.MISMATCH;
        for (FunctionSymbol function : functions) {
            List<ParameterSymbol> parameterList = function.getParameterList();
            SubtypeResult functionMatchingResult = SubtypeResult.SELF;
            if (argumentTypes.size() >= parameterList.size()) {
                continue;
            }
            for (int i = 0; i < argumentTypes.size(); i++) {
                Type argType = argumentTypes.get(i);
                Type paramType = parameterList.get(i).getType();
                functionMatchingResult = SubtypeResult.higher(functionMatchingResult, argType.isSubtypeOf(paramType));
            }
            if (functionMatchingResult.priority < foundMatchingResult.priority) {
                found = parameterList.get(argumentTypes.size()).getType();
                foundMatchingResult = functionMatchingResult;
            }
        }
        return found;
    }
}
