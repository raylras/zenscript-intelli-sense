package raylras.zen.code.symbol;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.FormalParameterContext;
import raylras.zen.code.resolve.TypeResolver;
import raylras.zen.code.type.Type;
import raylras.zen.util.CSTNodes;

public class ParameterSymbol extends Symbol {

    public ParameterSymbol(String name, FormalParameterContext cst, CompilationUnit unit) {
        super(name, cst, unit);
    }

    public boolean isOptional() {
        return getCst().defaultValue() != null;
    }

    @Override
    public Type getType() {
        return TypeResolver.getType(cst, unit);
    }

    @Override
    public Kind getKind() {
        return Kind.PARAMETER;
    }

    @Override
    public FormalParameterContext getCst() {
        return (FormalParameterContext) cst;
    }

}
