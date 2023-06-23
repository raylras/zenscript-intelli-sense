package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.resolve.ParameterResolver;
import raylras.zen.code.resolve.TypeResolver;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.Type;

import java.util.List;

public class FunctionSymbol extends Symbol {

    public FunctionSymbol(ParseTree owner, CompilationUnit unit) {
        super(owner, unit);
    }

    public List<VariableSymbol> getParameters() {
        return new ParameterResolver(unit).resolve(owner);
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
        Type type = new TypeResolver(unit).resolve(owner);
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

}
