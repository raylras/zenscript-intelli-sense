package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Declarator;
import raylras.zen.code.resolve.DeclaratorResolver;
import raylras.zen.code.scope.Scope;
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

    public abstract Kind getKind();

    public abstract List<Symbol> getMembers();

    public Scope getEnclosingScope() {
        Scope scope = unit.getScope(owner);
        if (scope == null)
            scope = unit.lookupScope(owner);
        return scope;
    }

    public Declarator getDeclarator() {
        return new DeclaratorResolver().resolve(owner);
    }

    public boolean isDeclaredBy(Declarator declarator) {
        return declarator == getDeclarator();
    }

    @Override
    public String toString() {
        return getName();
    }

    public enum Kind {
        CLASS, VARIABLE, FUNCTION, NONE
    }

}
