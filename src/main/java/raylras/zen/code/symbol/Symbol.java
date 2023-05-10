package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Declarator;
import raylras.zen.code.type.Type;

import java.util.Collections;
import java.util.List;

public abstract class Symbol {

    protected ParseTree owner;

    protected CompilationUnit unit;

    public Symbol(ParseTree owner, CompilationUnit unit) {
        this.owner = owner;
        this.unit = unit;
    }

    public abstract String getName();

    public abstract Type getType();

    public List<Symbol> getMembers() {
        return Collections.emptyList();
    }

    public ParseTree getOwner() {
        return owner;
    }

    public void setOwner(ParseTree owner) {
        this.owner = owner;
    }

    public CompilationUnit getCompilationUnit() {
        return unit;
    }

    public boolean isDeclaredBy(Declarator declarator) {
        return false;
    }

    @Override
    public String toString() {
        return getName();
    }

}
