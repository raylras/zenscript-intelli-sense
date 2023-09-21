package raylras.zen.util;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.symbol.Executable;
import raylras.zen.model.symbol.FunctionSymbol;
import raylras.zen.model.symbol.ParameterSymbol;
import raylras.zen.model.type.ClassType;
import raylras.zen.model.type.FunctionType;
import raylras.zen.model.type.SubtypeResult;
import raylras.zen.model.type.Type;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Executables {

    public static boolean areArgumentsMatch(Executable function, List<Type> argumentTypeList, CompilationEnvironment env) {
        return matchArguments(function, argumentTypeList, env).matched();
    }

    public static SubtypeResult matchArguments(Executable function, List<Type> argumentTypeList, CompilationEnvironment env) {
        List<ParameterSymbol> parameterList = function.getParameterList();
        SubtypeResult functionMatchingResult = SubtypeResult.SELF;
        int parameters = parameterList.size();
        int arguments = argumentTypeList.size();
        for (int i = 0; i < Math.max(parameters, arguments); i++) {
            if (i < parameters && i < arguments) {
                functionMatchingResult = SubtypeResult.higher(functionMatchingResult, argumentTypeList.get(i).testSubtypeOf(parameterList.get(i).getType(), env));
            } else if (i > arguments) {
                functionMatchingResult = SubtypeResult.higher(functionMatchingResult, parameterList.get(i).isOptional() ? SubtypeResult.SELF : SubtypeResult.MISMATCH);
            } else if (i > parameters) {
                ParameterSymbol lastParameter = parameterList.get(parameterList.size() - 1);
                if (lastParameter.isVararg()) {
                    functionMatchingResult = SubtypeResult.higher(functionMatchingResult, argumentTypeList.get(i).testSubtypeOf(lastParameter.getType(), env));
                } else {
                    functionMatchingResult = SubtypeResult.MISMATCH;
                }
            }
        }
        return functionMatchingResult;
    }

    public static Executable findBestMatch(List<Executable> functions, List<Type> argumentTypeList, CompilationEnvironment env) {
        return functions.stream()
                .min(Comparator.comparing(it -> matchArguments(it, argumentTypeList, env), SubtypeResult.PRIORITY_COMPARATOR))
                .orElse(null);
    }

    public static Type predictNextArgumentType(List<Executable> functions, List<Type> argumentTypes, CompilationEnvironment env) {
        Type found = null;
        SubtypeResult foundMatchingResult = SubtypeResult.MISMATCH;
        for (Executable function : functions) {
            List<ParameterSymbol> parameterList = function.getParameterList();
            SubtypeResult functionMatchingResult = SubtypeResult.SELF;
            if (argumentTypes.size() >= parameterList.size()) {
                continue;
            }
            for (int i = 0; i < argumentTypes.size(); i++) {
                Type argType = argumentTypes.get(i);
                Type paramType = parameterList.get(i).getType();
                functionMatchingResult = SubtypeResult.higher(functionMatchingResult, argType.testSubtypeOf(paramType, env));
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
