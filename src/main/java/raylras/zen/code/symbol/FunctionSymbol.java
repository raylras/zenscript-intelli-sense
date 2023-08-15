package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.TypeMatchingResult;
import raylras.zen.code.resolve.FormalParameterResolver;
import raylras.zen.code.resolve.TypeResolver;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.Type;
import raylras.zen.util.Symbols;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FunctionSymbol extends Symbol {

    public FunctionSymbol(ParseTree cst, CompilationUnit unit) {
        super(cst, unit);
    }

    /**
     * @deprecated Use {@link Symbols#findBestMatch(List, List)} instead.
     */
    @Deprecated
    public static FunctionSymbol match(List<FunctionSymbol> functions, List<Type> argumentTypes) {
        FunctionSymbol found = null;
        TypeMatchingResult foundMatchingResult = TypeMatchingResult.INVALID;
        for (FunctionSymbol function : functions) {
            List<ParameterSymbol> parameterList = function.getParameterList();
            TypeMatchingResult functionMatchingResult = TypeMatchingResult.EQUALS;
            if (argumentTypes.size() > parameterList.size()) {
                continue;
            }
            for (int i = 0; i < parameterList.size(); i++) {
                if (i < argumentTypes.size()) {
                    functionMatchingResult = functionMatchingResult.min(argumentTypes.get(i).canCastTo(parameterList.get(i).getType()));
                } else {
                    functionMatchingResult = functionMatchingResult.min(parameterList.get(i).hasDefaultValue() ? TypeMatchingResult.EQUALS : TypeMatchingResult.INVALID);
                }
            }
            if (functionMatchingResult.ordinal() < foundMatchingResult.ordinal()) {
                found = function;
                foundMatchingResult = functionMatchingResult;
            }
        }
        return found;
    }

    public static Type predictNextArgumentType(List<FunctionSymbol> functions, List<Type> argumentTypes) {
        Type found = null;
        TypeMatchingResult foundMatchingResult = TypeMatchingResult.INVALID;
        for (FunctionSymbol function : functions) {
            List<ParameterSymbol> parameterList = function.getParameterList();
            TypeMatchingResult functionMatchingResult = TypeMatchingResult.EQUALS;
            if (argumentTypes.size() >= parameterList.size()) {
                continue;
            }
            for (int i = 0; i < argumentTypes.size(); i++) {
                functionMatchingResult = functionMatchingResult.min(argumentTypes.get(i).canCastTo(parameterList.get(i).getType()));
            }
            if (functionMatchingResult.ordinal() < foundMatchingResult.ordinal()) {
                found = parameterList.get(argumentTypes.size()).getType();
                foundMatchingResult = functionMatchingResult;
            }
        }
        return found;
    }

    /**
     * @deprecated Use {@link Symbols#getMembersByName(Type, String, Class)} instead.
     */
    @Deprecated
    public static List<FunctionSymbol> find(Type owner, String name) {
        List<FunctionSymbol> functions = new ArrayList<>();
        for (Symbol member : owner.getMembers()) {
            if (Objects.equals(member.getSimpleName(), name) && member instanceof FunctionSymbol) {
                functions.add(((FunctionSymbol) member));
            }
        }
        return functions;
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
