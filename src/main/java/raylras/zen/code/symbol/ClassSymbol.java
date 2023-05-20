package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.resolve.NameResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.Type;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collections;
import java.util.List;

public class ClassSymbol extends Symbol {

    public ClassSymbol(ParseTree owner, CompilationUnit unit) {
        super(owner, unit);
    }

    @Override
    public String getName() {
        return new NameResolver().resolve(getOwner());
    }

    @Override
    public Type getType() {
        String qualifiedName = new NameResolver().resolve(getOwner());
        return new ClassType(qualifiedName);
    }

    public FunctionSymbol getFunctionalInterface() {
        throw new NotImplementedException();
    }

    @Override
    public ZenSymbolKind getKind() {
        return ZenSymbolKind.ZEN_CLASS;
    }

    @Override
    public List<Symbol> getMembers() {
        Scope scope = getUnit().getScope(getOwner());
        if (scope != null)
            return scope.symbols;
        return Collections.emptyList();
    }

}
