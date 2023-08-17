package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.resolve.FormalParameterResolver;
import raylras.zen.code.resolve.TypeResolver;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.Type;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionSymbol extends Symbol {

    public FunctionSymbol(String name, ParseTree cst, CompilationUnit unit) {
        super(name, cst, unit);
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
                .map(ParameterSymbol::getNameWithType)
                .collect(Collectors.joining(", ", getSimpleName() + "(", ") as " + getReturnType()));
    }

}
