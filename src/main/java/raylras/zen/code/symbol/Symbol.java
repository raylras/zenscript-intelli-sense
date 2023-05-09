package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.Type;

public abstract class Symbol {

    public Scope enclScope;
    public ParseTree owner;

    protected CompilationUnit unit;

    public Symbol(Scope enclScope, ParseTree owner, CompilationUnit unit) {
        this.enclScope = enclScope;
        this.owner = owner;
        this.unit = unit;
    }

    public abstract String getName();

    public abstract Type getType();

}
