package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.Type;

public abstract class Symbol {

    protected Scope enclScope;
    protected ParseTree owner;

    protected CompilationUnit unit;

    public Symbol(Scope enclScope, ParseTree owner, CompilationUnit unit) {
        this.enclScope = enclScope;
        this.owner = owner;
        this.unit = unit;
    }

    public abstract String getName();

    public abstract Type getType();

    public Scope getEnclScope() {
        return enclScope;
    }

    public void setEnclScope(Scope enclScope) {
        this.enclScope = enclScope;
    }

    public ParseTree getOwner() {
        return owner;
    }

    public void setOwner(ParseTree owner) {
        this.owner = owner;
    }

}
