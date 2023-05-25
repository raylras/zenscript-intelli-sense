package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Declarator;
import raylras.zen.code.resolve.DeclaratorResolver;
import raylras.zen.code.type.Type;

import java.util.List;

public abstract class Symbol {

    protected ParseTree owner;
    protected CompilationUnit unit;

    public Symbol(ParseTree owner, CompilationUnit unit) {
        this.owner = owner;
        this.unit = unit;
    }

    public abstract String getSimpleName();

    public abstract String getQualifiedName();

    public abstract Type getType();

    public abstract Kind getKind();

    public abstract List<Symbol> getMembers();

    public Declarator getDeclarator() {
        return new DeclaratorResolver().resolve(owner);
    }

    public boolean isDeclaredBy(Declarator declarator) {
        return declarator == getDeclarator();
    }

    public ParseTree getOwner() {
        return owner;
    }

    public CompilationUnit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return getSimpleName();
    }

    public enum Kind {
        CLASS, VARIABLE, FUNCTION, NONE
    }

}
