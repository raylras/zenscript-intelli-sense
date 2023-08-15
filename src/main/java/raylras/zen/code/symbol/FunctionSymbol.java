package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.resolve.FormalParameterResolver;
import raylras.zen.code.resolve.TypeResolver;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.SubtypeResult;
import raylras.zen.code.type.Type;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionSymbol extends Symbol {

    public FunctionSymbol(ParseTree cst, CompilationUnit unit) {
        super(cst, unit);
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
                functionMatchingResult = functionMatchingResult.and(argType.isSubtypeOf(paramType));
            }
            if (functionMatchingResult.priority < foundMatchingResult.priority) {
                found = parameterList.get(argumentTypes.size()).getType();
                foundMatchingResult = functionMatchingResult;
            }
        }
        return found;
    }

    public List<ParameterSymbol> getParameterList() {
        return FormalParameterResolver.getFormalParameterList(cst, unit);
    }

    public List<Type> getParameterTypeList() {
        return getParameterList().stream()
                .map(ParameterSymbol::getType)
                .collect(Collectors.toList());
    }

    public Type getReturnType() {
        FunctionType type = getType();
        if (type != null) {
            return type.getReturnType();
        } else {
            return null;
        }
    }

    @Override
    public FunctionType getType() {
        Type type = TypeResolver.getType(cst, unit);
        if (type instanceof FunctionType) {
            return (FunctionType) type;
        } else {
            return null;
        }
    }

    @Override
    public Kind getKind() {
        return Kind.FUNCTION;
    }

    @Override
    public String getNameWithType() {
        // add(a as int, b as int) as int
        return getParameterList().stream()
                .map(VariableSymbol::getNameWithType)
                .collect(Collectors.joining(", ", getSimpleName() + "(", ") as " + getReturnType()));
    }

}
