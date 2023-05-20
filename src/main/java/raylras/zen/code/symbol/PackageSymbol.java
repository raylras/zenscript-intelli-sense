package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.type.Type;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public abstract class PackageSymbol extends Symbol {
    public PackageSymbol(ParseTree owner, CompilationUnit unit) {
        super(owner, unit);
    }

    @Override
    public Type getType() {
        throw new NotImplementedException();
    }
}
