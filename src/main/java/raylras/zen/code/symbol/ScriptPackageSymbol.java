package raylras.zen.code.symbol;

import com.google.common.collect.ImmutableList;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.scope.Scope;

import java.util.Collections;
import java.util.List;

// TODO: use sub package name (before class name)?
public class ScriptPackageSymbol extends PackageSymbol {
    public ScriptPackageSymbol(ParseTree owner, CompilationUnit unit) {
        super(owner, unit);
    }

    @Override
    public String getName() {
        return getUnit().relativePath();
    }

    @Override
    public ZenSymbolKind getKind() {
        return ZenSymbolKind.LIBRARY_PACKAGE;
    }

    @Override
    public List<Symbol> getMembers() {
        return ImmutableList.copyOf(getUnit().getTopLevelSymbols());
    }
}
