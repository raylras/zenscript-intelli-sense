package raylras.zen.code.type;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.symbol.Symbol;

public class ClassType extends Type {

    public String qualifiedName;

    public ClassType(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    @Override
    public Kind getKind() {
        return Kind.CLASS;
    }

    @Override
    public Symbol lookupSymbol(CompilationUnit unit) {
        return unit.lookupSymbol(qualifiedName);
    }

    @Override
    public String toString() {
        return qualifiedName;
    }

}
