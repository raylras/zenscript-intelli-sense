package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.resolve.VariableTypeResolver;
import raylras.zen.code.type.Type;

import java.util.Collections;
import java.util.List;

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

    @Override
    public List<Symbol> getMembers() {
        return Collections.emptyList();
    }

}
