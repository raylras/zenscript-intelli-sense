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
        if (symbol != null) {
            return symbol.getName();
        }
        return owner.accept(new NameResolver());
    }

    public String getSimpleName() {
        String name = getName();
        int i = name.lastIndexOf('.');
        if (i >= 0) {
            return name.substring(i + 1);
        }
        return name;
    }

    @Override
    public String toString() {
        return getSimpleName();
    }

}
