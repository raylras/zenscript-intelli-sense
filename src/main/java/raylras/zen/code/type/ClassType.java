package raylras.zen.code.type;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.resolve.NameResolver;
import raylras.zen.code.symbol.Symbol;

public class ClassType extends Type {

    private ParseTree owner;
    private CompilationUnit unit;

    public ClassType(ParseTree owner, CompilationUnit unit) {
        this.owner = owner;
        this.unit = unit;
    }

    @Override
    public Kind getKind() {
        return Kind.CLASS;
    }

    @Override
    public Symbol lookupSymbol() {
        return unit.lookupSymbol(owner);
    }

    public String getName() {
        Symbol symbol = lookupSymbol();
        if (symbol != null)
            return symbol.getName();
        return new NameResolver().resolve(owner);
    }

    @Override
    public String toString() {
        return getName();
    }

}
