package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Declarator;
import raylras.zen.code.type.Type;

import java.util.List;

public abstract class Symbol {

    public ParseTree owner;

    public CompilationUnit unit;

    public Symbol(ParseTree owner, CompilationUnit unit) {
        this.owner = owner;
        this.unit = unit;
    }

    public abstract String getName();

    public abstract Type getType();

    public abstract List<Symbol> getMembers();

    public Declarator getDeclarator() {
        return null;
    }

    public boolean isDeclaredBy(Declarator declarator) {
        return false;
    }

    @Override
    public String toString() {
        return getName();
    }

}
