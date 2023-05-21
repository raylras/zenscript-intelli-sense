package raylras.zen.code.symbol;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.data.Declarator;
import raylras.zen.code.type.resolve.DeclaratorResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.Type;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class Symbol {

    private final ParseTree owner;

    private final CompilationUnit unit;

    public Symbol(ParseTree owner, CompilationUnit unit) {
        this.owner = owner;
        this.unit = unit;
    }

    public abstract String getName();

    public abstract Type getType();

    public abstract ZenSymbolKind getKind();

    public abstract List<Symbol> getMembers();

    public boolean isValid() {
        return true;
    }

    public boolean isLibrarySymbol() {
        return this.unit.isDzs();
    }

    public Scope getEnclosingScope() {
        Scope scope = getUnit().getScope(getOwner());
        if (scope == null)
            scope = getUnit().lookupScope(getOwner());
        return scope;
    }

    public Declarator getDeclarator() {
        return new DeclaratorResolver().resolve(getOwner());
    }

    public boolean isDeclaredBy(Declarator declarator) {
        return declarator == getDeclarator();
    }


    @Override
    public String toString() {
        return getName();
    }

    public ParseTree getOwner() {
        return owner;
    }

    public CompilationUnit getUnit() {
        return unit;
    }

    public Map<String, String> getAnnotations() {
        return Collections.emptyMap();
    }

    public boolean isHidden() {
        return false;
    }
}
