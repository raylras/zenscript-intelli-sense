package raylras.zen.code.symbol;

import com.google.common.collect.ImmutableList;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.scope.Scope;
import raylras.zen.service.EnvironmentService;

import java.util.List;

public class LibraryPackageSymbol extends PackageSymbol {
    private final String name;
    private final EnvironmentService environment;

    public LibraryPackageSymbol(String name, EnvironmentService environment) {
        super(null, null);
        this.name = name;
        this.environment = environment;
    }

    @Override
    public ParseTree getOwner() {
        throw new IllegalStateException();
    }

    @Override
    public CompilationUnit getUnit() {
        throw new IllegalStateException();
    }

    @Override
    public Scope getEnclosingScope() {
        return environment.getRootScope();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ZenSymbolKind getKind() {
        return ZenSymbolKind.LIBRARY_PACKAGE;
    }

    @Override
    public List<Symbol> getMembers() {
        return ImmutableList.copyOf(environment.getClassSymbolsByPackageName(name));
    }

    @Override
    public boolean isLibrarySymbol() {
        return true;
    }
}
