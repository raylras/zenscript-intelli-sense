package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.resolve.TypeResolver;
import raylras.zen.code.type.Type;

public class VariableSymbol extends Symbol {

    public VariableSymbol(ParseTree cst, CompilationUnit unit) {
        super(cst, unit);
    }

    @Override
    public Type getType() {
        return TypeResolver.getType(cst, unit);
    }

    @Override
    public Kind getKind() {
        return Kind.VARIABLE;
    }

}
