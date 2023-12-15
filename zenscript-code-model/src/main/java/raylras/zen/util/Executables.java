package raylras.zen.util;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.symbol.Executable;
import raylras.zen.model.symbol.ParameterSymbol;
import raylras.zen.model.type.SubtypeResult;
import raylras.zen.model.type.Type;
import raylras.zen.model.type.Types;

import java.util.Comparator;
import java.util.List;

public class Executables {

    public static boolean areArgumentsMatch(Executable executable, List<Type> argumentTypeList, CompilationEnvironment env) {
        return matchArguments(executable, argumentTypeList, env).matched();
    }

    public static SubtypeResult matchArguments(Executable executable, List<Type> argumentTypeList, CompilationEnvironment env) {
        List<ParameterSymbol> parameterList = executable.getParameterList();
        SubtypeResult functionMatchingResult = SubtypeResult.SELF;
        int paramSize = parameterList.size();
        int argSize = argumentTypeList.size();
        for (int i = 0; i < Math.max(paramSize, argSize); i++) {
            if (i < paramSize && i < argSize) {
                functionMatchingResult = SubtypeResult.lower(functionMatchingResult, Types.test(argumentTypeList.get(i), parameterList.get(i).getType(), env));
            } else if (i > argSize) {
                functionMatchingResult = SubtypeResult.lower(functionMatchingResult, parameterList.get(i).isOptional() ? SubtypeResult.SELF : SubtypeResult.MISMATCH);
            } else if (i > paramSize) {
                ParameterSymbol lastParameter = parameterList.get(parameterList.size() - 1);
                if (lastParameter.isVararg()) {
                    functionMatchingResult = SubtypeResult.lower(functionMatchingResult, Types.test(argumentTypeList.get(i), lastParameter.getType(), env));
                } else {
                    functionMatchingResult = SubtypeResult.MISMATCH;
                }
            }
        }
        return functionMatchingResult;
    }

    public static Executable findBestMatch(List<Executable> functions, List<Type> argumentTypeList, CompilationEnvironment env) {
        return functions.stream()
                .max(Comparator.comparing(it -> matchArguments(it, argumentTypeList, env), SubtypeResult.PRIORITY_COMPARATOR))
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
                functionMatchingResult = SubtypeResult.lower(functionMatchingResult, Types.test(argType, paramType, env));
            }
            if (functionMatchingResult.getPriority() > foundMatchingResult.getPriority()) {
                found = parameterList.get(argumentTypes.size()).getType();
                foundMatchingResult = functionMatchingResult;
            }
        }
        return found;
    }

}
