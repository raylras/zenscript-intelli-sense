package raylras.zen.util;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.symbol.FunctionSymbol;
import raylras.zen.code.symbol.ParameterSymbol;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.SubtypeResult;
import raylras.zen.code.type.Type;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Functions {

    public static boolean areArgumentsMatch(FunctionSymbol function, List<Type> argumentTypeList, CompilationEnvironment env) {
        return matchArguments(function, argumentTypeList, env).matched();
    }

    public static SubtypeResult matchArguments(FunctionSymbol function, List<Type> argumentTypeList, CompilationEnvironment env) {
        List<ParameterSymbol> parameterList = function.getParameterList();
        SubtypeResult functionMatchingResult = SubtypeResult.SELF;
        int parameters = parameterList.size();
        int arguments = argumentTypeList.size();
        for (int i = 0; i < Math.max(parameters, arguments); i++) {
            if (i < parameters && i < arguments) {
                functionMatchingResult = SubtypeResult.higher(functionMatchingResult, argumentTypeList.get(i).isSubtypeOf(parameterList.get(i).getType(), env));
            } else if (i > arguments) {
                functionMatchingResult = SubtypeResult.higher(functionMatchingResult, parameterList.get(i).isOptional() ? SubtypeResult.SELF : SubtypeResult.MISMATCH);
            } else if (i > parameters) {
                ParameterSymbol lastParameter = parameterList.get(parameterList.size() - 1);
                if (lastParameter.isVararg()) {
                    functionMatchingResult = SubtypeResult.higher(functionMatchingResult, argumentTypeList.get(i).isSubtypeOf(lastParameter.getType(), env));
                } else {
                    functionMatchingResult = SubtypeResult.MISMATCH;
                }
            }
        }
        return functionMatchingResult;
    }

    public static FunctionSymbol findBestMatch(List<FunctionSymbol> functions, List<Type> argumentTypeList, CompilationEnvironment env) {
        return functions.stream()
                .min(Comparator.comparing(it -> matchArguments(it, argumentTypeList, env), SubtypeResult.PRIORITY_COMPARATOR))
                .orElse(null);
    }

    public static Type predictNextArgumentType(List<FunctionSymbol> functions, List<Type> argumentTypes, CompilationEnvironment env) {
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
                functionMatchingResult = SubtypeResult.higher(functionMatchingResult, argType.isSubtypeOf(paramType, env));
            }
            if (functionMatchingResult.priority < foundMatchingResult.priority) {
                found = parameterList.get(argumentTypes.size()).getType();
                foundMatchingResult = functionMatchingResult;
            }
        }
        return found;
    }

    public static Optional<FunctionType> findLambdaForm(ClassType type, CompilationEnvironment env) {
        return Symbols.getMembersByName(type, "", FunctionSymbol.class, env)
                .stream()
                .map(FunctionSymbol::getType)
                .findFirst();
    }

}
