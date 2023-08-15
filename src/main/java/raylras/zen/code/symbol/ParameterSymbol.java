package raylras.zen.code.symbol;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.FormalParameterContext;

public class ParameterSymbol extends VariableSymbol {

    public ParameterSymbol(FormalParameterContext cst, CompilationUnit unit) {
        super(cst, unit);
    }

    public boolean isOptional() {
        return getCst().defaultValue() != null;
    }

    @Override
    public FormalParameterContext getCst() {
        return (FormalParameterContext) cst;
    }

}
