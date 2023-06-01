package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.resolve.VariableTypeResolver;
import raylras.zen.code.type.Type;

public class VariableSymbol extends Symbol {

    public VariableSymbol(ParseTree owner, CompilationUnit unit) {
        super(owner, unit);
    }

    @Override
    public Type getType() {
        return new VariableTypeResolver(unit).resolve(owner);
    }

    @Override
    public Kind getKind() {
        return Kind.VARIABLE;
    }

}
