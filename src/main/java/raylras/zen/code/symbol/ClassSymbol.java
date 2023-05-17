package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.resolve.NameResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.Type;

import java.util.Collections;
import java.util.List;

public class ClassSymbol extends Symbol {

    public ClassSymbol(ParseTree owner, CompilationUnit unit) {
        super(owner, unit);
    }

    @Override
    public String getName() {
        return new NameResolver().resolve(owner);
    }

    @Override
    public Type getType() {
        return new ClassType(owner, unit);
    }

    @Override
    public List<Symbol> getMembers() {
        Scope scope = unit.getScope(owner);
        if (scope != null)
            return scope.symbols;
        return Collections.emptyList();
    }

}
