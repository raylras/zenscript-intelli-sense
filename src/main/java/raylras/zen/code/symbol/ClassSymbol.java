package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.resolve.NameResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.Type;

import java.util.List;

public class ClassSymbol extends Symbol {

    public Symbol superClass;
    public List<Symbol> interfaces;

    public ClassSymbol(Scope enclScope, ParseTree owner, CompilationUnit unit) {
        super(enclScope, owner, unit);
    }

    @Override
    public String getName() {
        return owner.accept(new NameResolver());
    }

    @Override
    public Type getType() {
        return new ClassType(getName(), unit);
    }

}
